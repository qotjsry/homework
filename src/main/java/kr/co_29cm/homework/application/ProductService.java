package kr.co_29cm.homework.application;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import kr.co_29cm.homework.domain.ProductRepository;
import kr.co_29cm.homework.support.CsvDataReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @PostConstruct
    public void saveProductCsvData() throws IOException {
        CsvDataReader csvDataReader = new CsvDataReader();
        csvDataReader.readDataFromCsv().stream().forEach(productRepository::save);
    }

}
