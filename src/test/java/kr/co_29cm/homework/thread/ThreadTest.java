package kr.co_29cm.homework.thread;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import kr.co_29cm.homework.domain.InMemoryOrderRepository;
import kr.co_29cm.homework.domain.InMemoryProductRepository;
import kr.co_29cm.homework.domain.OrderItem;
import kr.co_29cm.homework.domain.Product;
import kr.co_29cm.homework.domain.ProductInfo;
import kr.co_29cm.homework.domain.ProductStock;
import kr.co_29cm.homework.exception.SoldOutException;
import kr.co_29cm.homework.view.ConsoleView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThreadTest {

    private ConsoleView consoleView;

    @BeforeEach
    public void setUp() {
        consoleView = new ConsoleView(new InMemoryProductRepository(),new InMemoryOrderRepository());
    }

    @Test
    void runAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        });
        future.get();
        System.out.println("Thread: " + Thread.currentThread().getName());
    }

    @Test
    public void multiThreadTest() throws Exception {
        List<OrderItem> orderItems = getOrderItems();

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

        assertEquals(true, resultFuture.get());
    }

    private static List<OrderItem> getOrderItems() {
        Product product_5 = Product.of(1234L, "상품명", 21000L, 5);
        Product product_10 = Product.of(1235L, "상품명", 21000L, 10);
        Product product_15 = Product.of(1236L, "상품명", 21000L,15);

        OrderItem orderItem_5 = new OrderItem(product_5, 3);
        OrderItem orderItem_10 = new OrderItem(product_10, 8);
        OrderItem orderItem_15 = new OrderItem(product_15, 20);

        List<OrderItem> orderItems = List.of(orderItem_5, orderItem_10, orderItem_15);
        return orderItems;
    }


}
