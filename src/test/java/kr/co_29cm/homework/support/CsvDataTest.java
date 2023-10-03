package kr.co_29cm.homework.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.co_29cm.homework.application.ProductService;
import kr.co_29cm.homework.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CsvDataTest {

    @Autowired
    ProductService productService;
    
    @Test
    @DisplayName("csv 데이터 추출 테스트")
    public void csvDataTest() throws Exception {
        CsvDataReader csvDataReader = new CsvDataReader();
        List<Product> products = csvDataReader.readDataFromCsv();
        assertThat(products.size()).isEqualTo(19);
    }

}
