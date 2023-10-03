package kr.co_29cm.homework.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품번호로 상품 조회")
    public void findByProductNumberTest() throws Exception {
        Product product = Product.of(768848L, "[STANLEY] GO CERAMIVAC 진공 텀블러/보틀 3종",
            21000L,45);
        productRepository.save(product);

        Product findProduct = productRepository.findByProductNumber(768848L);
        assertThat(product).isEqualTo(findProduct);
    }

}
