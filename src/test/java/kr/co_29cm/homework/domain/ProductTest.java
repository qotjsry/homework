package kr.co_29cm.homework.domain;


import static org.assertj.core.api.Assertions.assertThat;


import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

public class ProductTest {
    private Product product;

    @BeforeEach
    public void setUp() throws Exception {
        product = product.of(1234L, "상품명", 21000L, 10);
    }

    @Test
    @DisplayName("상품 생성")
    public void productCreate() throws Exception {
        assertThat(product.getProductNumber()).isEqualTo(1234L);
    }

    @Test
    @DisplayName("상품 재고 부족 테스트")
    public void productAvailable() throws Exception {
        assertThat(product.isStockAvailable(11)).isEqualTo(false);
    }

    @Test
    @DisplayName("상품 재고 차감 테스트")
    public void productMinus() throws Exception {
        product.minusStock(1);
        assertThat(product.getStock()).isEqualTo(9);
    }

}
