package kr.co_29cm.homework.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository{
    private final Map<Long, Product> products = new HashMap<>();

    @Override
    public void updateStock(Long productNumber, int quantity) {
        products.get(productNumber).minusStock(quantity);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product save(Product product) {
        return products.put(product.getProductNumber(), product);
    }

    @Override
    public Product findByProductNumber(Long productNumber) {
        return products.get(productNumber);
    }
}
