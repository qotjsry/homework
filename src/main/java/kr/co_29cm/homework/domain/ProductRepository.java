package kr.co_29cm.homework.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository {


    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stock.stock = p.stock.stock - ?2 WHERE p.productNumber = ?1")
    void updateStock(Long productNumber, int quantity);

    List<Product> findAll();
    Product save(Product product);
    Product findByProductNumber(Long productNumber);
}
