package kr.co_29cm.homework;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import kr.co_29cm.homework.application.ProductService;
import kr.co_29cm.homework.view.ConsoleView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HomeworkApplication {

    public static void main(String[] args)
        throws IOException, ExecutionException, InterruptedException {
        SpringApplication app = new SpringApplication(HomeworkApplication.class);
        ConfigurableApplicationContext context = app.run(args);

        ConsoleView consoleView = context.getBean(ConsoleView.class);
        OrderSystem orderSystem = new OrderSystem(consoleView);

        orderSystem.start();
    }

}
