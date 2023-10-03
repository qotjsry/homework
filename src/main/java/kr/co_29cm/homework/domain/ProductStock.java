package kr.co_29cm.homework.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class ProductStock {
    private int stock;

    public ProductStock(int stock) {
        this.stock = stock;
    }

}
