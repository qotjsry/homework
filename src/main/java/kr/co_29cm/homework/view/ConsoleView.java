package kr.co_29cm.homework.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kr.co_29cm.homework.domain.Order;
import kr.co_29cm.homework.domain.OrderItem;
import kr.co_29cm.homework.domain.OrderRepository;
import kr.co_29cm.homework.domain.Product;
import kr.co_29cm.homework.domain.ProductRepository;
import kr.co_29cm.homework.exception.SoldOutException;
import kr.co_29cm.homework.support.MoneyRule;
import kr.co_29cm.homework.support.OrderProcess;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ConsoleView {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleView(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public OrderProcess selectOrderOrQuit() throws IOException {
        System.out.print("입력(o[order]: 주문, q[quit]: 종료): ");
        String input = reader.readLine().trim();
        if (input.equals("o") || input.equals("order")) {
            return OrderProcess.ORDER;
        }
        if (input.equals("q") || input.equals("quit")) {
            return OrderProcess.QUIT;
        }
        System.out.println("입력 값을 확인해 주세요.");
        return OrderProcess.IO_EXCEPTION;
    }


    public void startOrder() throws IOException {
        List<OrderItem> orderItems = new ArrayList<>();
        Boolean isSoldOut = false;
        Order order = Order.of(orderItems);
        while (true) {
            String productNumber = selectProductNumber();
            if (productNumber.isEmpty()) {
                break;
            }
            Product product = productRepository.findByProductNumber(Long.parseLong(productNumber));

            if (product == null) {
                System.out.println("상품번호가 존재하지 않습니다. 다시 입력해주세요. ");
                continue;
            }
            String quantity = selectQuantity();
            if (quantity.isEmpty()) {
                break;
            }
            if (!isValidQuantity(quantity)) {
                System.out.println("수량은 숫자만 입력해주세요.");
                continue;
            }
            order.add(new OrderItem(product, Integer.parseInt(quantity)));
        }
        if (orderItems.size() > 0) {

            isSoldOut = checkSoldOut(orderItems);
            if (!isSoldOut) {
                showOrderList(order);
                orderRepository.save(order);
                for (OrderItem orderItem : orderItems) {
                    productRepository.updateStock(orderItem.getProduct().getProductNumber(), orderItem.getQuantity());
                }
            }
        }

        if (isSoldOut) {
            System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
            throw new SoldOutException();
        }
    }

    @Transactional(readOnly = true)
    public void showProductList() {
        System.out.println("상품번호\t상품명\t판매가격\t재고수");

        productRepository.findAll().stream().map(Product::toString).forEach(System.out::println);
    }

    public void showOrderList(Order order) {
        System.out.println("주문 내역: ");
        printSeparator();
        printOrderProducts(order);
        printSeparator();
        printOrderPrice(order);
        printSeparator();
        printPaymentPrice(order);
        printSeparator();
    }

    private void printPaymentPrice(Order order) {
        System.out.println("결제금액: " + order.getPaymentPrice());
    }

    private void printOrderPrice(Order order) {
        System.out.println("주문금액: " + order.getPrice());
        if (!order.isDeliveryFree()) {
            System.out.println("배송비: " + MoneyRule.DELIVERY_PRICE);
        }
    }

    private void printOrderProducts(Order order) {
        System.out.println(order.getProductInfo());
    }


    public String selectProductNumber() throws IOException {
        System.out.print("상품번호: ");
        return reader.readLine().trim();
    }

    public String selectQuantity() throws IOException {
        System.out.print("수량: ");
        return reader.readLine();
    }


    public boolean checkSoldOut(List<OrderItem> orderItems) {
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    if (!orderItem.isStockAvailable()) {
                        throw new SoldOutException();
                    }
                    return false;
                } catch (SoldOutException e) {
                    return true;
                }
            });
            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        CompletableFuture<Boolean> resultFuture = allOf.thenApply(v -> {
            return futures.stream().anyMatch(CompletableFuture::join);
        });

        // 결과 반환
        return resultFuture.join();
    }

    private boolean isValidQuantity(String quantity) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(quantity);
        return matcher.matches();
    }

    private void printSeparator() {
        System.out.println("------------------------------------------------------------");
    }

    public void endOrder() {
        System.out.println("고객님의 주문 감사합니다.");
    }
}
