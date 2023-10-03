package kr.co_29cm.homework.domain;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderTest {
    private OrderItem orderItem;

    @BeforeEach
    public void setUp() throws Exception {
        orderItem = new OrderItem(Product.of(1234L,"상품명", 21000L,45), 2);
    }

    @Test
    @DisplayName("주문 가격 계산")
    public void orderPrice() throws Exception {
        List<OrderItem> orderItems = new ArrayList<>();
        Order order = Order.of(orderItems);
        order.add(orderItem);

        assertThat(order.getPrice()).isEqualTo(42000L);
    }

    @Test
    @DisplayName("주문 금액 50000원 이하 일때 배달 비용 추가")
    public void orderPayment() throws Exception {
        Order order = Order.of(List.of(orderItem));
        assertThat(order.getPaymentPrice()).isEqualTo(42000L + 2500L);
    }


}
