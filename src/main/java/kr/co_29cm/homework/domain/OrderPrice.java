package kr.co_29cm.homework.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class OrderPrice {

    private BigDecimal price;

    public OrderPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getValue() {
        return price;
    }

}
