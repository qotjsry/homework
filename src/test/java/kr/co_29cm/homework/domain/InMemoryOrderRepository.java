package kr.co_29cm.homework.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> orders = new HashMap<>();
    @Override
    public Order save(Order order) {
        return orders.put(order.getId(), order);
    }
}
