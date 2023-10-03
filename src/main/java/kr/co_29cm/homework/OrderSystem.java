package kr.co_29cm.homework;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import kr.co_29cm.homework.support.OrderProcess;
import kr.co_29cm.homework.view.ConsoleView;

public class OrderSystem {

    private final ConsoleView consoleView;

    public OrderSystem(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public void start() throws IOException, ExecutionException, InterruptedException {
        while (true) {
            OrderProcess orderProcess = consoleView.selectOrderOrQuit();
            if(orderProcess.isIoException()) {
                continue;
            }
            if (orderProcess.isContinue()) {
                consoleView.showProductList();
                consoleView.startOrder();
            } else {
                consoleView.endOrder();
                break;
            }
        }
    }

}
