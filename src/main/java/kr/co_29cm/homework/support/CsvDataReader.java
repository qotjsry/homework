package kr.co_29cm.homework.support;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import kr.co_29cm.homework.domain.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.ResourceUtils;

public class CsvData {

    public List<Product> readDataFromCsv() throws IOException {
        String csvFilePath = ResourceUtils.getFile("classpath:items.csv").getPath();

        List<Product> dataList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(csvFilePath);
            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(fileReader)) {

            for (CSVRecord record : csvParser) {
                String productNumber = record.get("상품번호");
                String productName = record.get("상품명");
                BigDecimal price = BigDecimal.valueOf(Long.parseLong(record.get("판매가격")));
                int quantity = Integer.parseInt(record.get("재고수량"));

                Product product = new Product(productNumber, productName, price, quantity);
                dataList.add(product);
            }
        }
        return dataList;
    }
}
