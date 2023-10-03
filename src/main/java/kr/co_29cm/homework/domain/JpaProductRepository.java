package kr.co_29cm.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaProductRepository extends ProductRepository , JpaRepository<Product, Long>{
    @Query("select p from Product p where p.productNumber = :productNumber")
    Product findByProductNumber(Long productNumber);

}
