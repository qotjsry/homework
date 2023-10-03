package kr.co_29cm.homework.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInfo {


    private String name;
    private BigDecimal price;

    public ProductInfo(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public static ProductInfo of(String name, Long price) {
        return new ProductInfo(name, BigDecimal.valueOf(price));
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name +
            "\t" + price.longValue() +
            "\t";
    }
}
