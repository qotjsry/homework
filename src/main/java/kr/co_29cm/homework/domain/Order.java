package kr.co_29cm.homework.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kr.co_29cm.homework.support.MoneyRule;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    private UUID id;
    @Embedded
    private OrderPrice price;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> orderItems;

    public Order(UUID id, List<OrderItem> orderItems, OrderPrice price) {
        this.id = id;
        this.orderItems = orderItems;
        this.price = price;
    }
    public static Order of(List<OrderItem> orderItems) {
        return new Order(UUID.randomUUID(), orderItems, new OrderPrice(orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO)));
    }

    public void add(OrderItem orderItem) {
        orderItem.setOrder(this);
        orderItems.add(orderItem);
        price = new OrderPrice(orderItem.getPrice().add(price.getValue()));
    }
    public UUID getId() {
        return id;
    }

    public Long getPrice() {
        return price.getValue().longValue();
    }

    public Long getPaymentPrice() {
        if(isDeliveryFree()) {
            return price.getValue().longValue();
        }
        return price.getValue().add(BigDecimal.valueOf(MoneyRule.DELIVERY_PRICE)).longValue();
    }

    public boolean isDeliveryFree() {
        return price.getValue().compareTo(BigDecimal.valueOf(MoneyRule.DELIVERY_FREE_ORDER_PRICE)) >= 0;
    }

    public String getProductInfo() {
        return orderItems.stream().map(OrderItem::getProductInfo).collect(Collectors.joining("\n")).toString();
    }

}
