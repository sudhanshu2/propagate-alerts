package visualizer;

import java.util.concurrent.TimeUnit;

public class ConsoleOutput {
    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 100; i++) {
            System.out.print("\r" + "*".repeat(i));
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
