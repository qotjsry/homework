package kr.co_29cm.homework.domain;

import java.util.List;

public class InMemoryProductRepository implements ProductRepository{

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Product findByProductNumber(Long productNumber) {
        return null;
    }
}
