import java.util.Scanner;
import java.util.concurrent.*;

public class Second {
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        while(true) {
            System.out.println("Enter the digit, please: ");
            int digit = scr.nextInt();
            Callable<String> task1 = () -> {
                Thread.sleep(1000+(int) (Math.random()*4000));
                System.out.println("For digit: "+ digit + " pow is " +(digit *digit));
                return String.valueOf(digit *digit);
            };
            FutureTask<String> future = new FutureTask<>(task1);
            new Thread(future).start();
        }
    }
}
