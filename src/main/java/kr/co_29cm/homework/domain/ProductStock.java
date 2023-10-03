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

    public boolean isAvailable(int quantity) {
        return stock >= quantity;
    }

    public int getValue() {
        return stock;
    }

    @Override
    public String toString() {
        return String.valueOf(stock);
    }

    public void minus(int quantity) {
        stock -= quantity;
    }
}
