package kr.co_29cm.homework.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @Column(name = "product_number")
    private Long productNumber;
    @Embedded
    private ProductInfo productInfo;
    @Embedded
    private ProductStock stock;

    public Product(Long productNumber, ProductInfo productInfo, ProductStock stock) {
        this.productNumber = productNumber;
        this.productInfo = productInfo;
        this.stock = stock;
    }

    public static Product of(Long productNumber, String name, Long price, int stock) {
        return new Product(productNumber, ProductInfo.of(name,price), new ProductStock(stock));
    }

    public boolean isStockAvailable(int quantity) {
        return stock.isAvailable(quantity);
    }
    public void minusStock(int quantity) {
        stock.minus(quantity);
    }
    public Long getProductNumber() {
        return productNumber;
    }

    public String getName() {
        return productInfo.getName();
    }

    public BigDecimal getPrice() {
        return productInfo.getPrice();
    }

    public int getStock() {
        return stock.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(productNumber, product.productNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productNumber);
    }

    @Override
    public String toString() {
        return  productNumber +
            "\t" +productInfo +
            "\t" + stock;
    }


}
