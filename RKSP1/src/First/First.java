import java.util.Random;
import java.util.concurrent.*;

//1. Поиск максимального элемента в массиве.

public class First {
   //Функция поиска максимального элемента
    public static void subsequence(int arr[]){
        int temp=1;
        temp=arr[0];
        for(int i = 0; i<10000; i++) {
            if(arr[i]>temp){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                temp = arr[i];
            }
        }
        System.out.println("Subsequence: " + temp);
    }

    public static void space(){
        System.out.println();
    }
    public static void main(String[] args) {
        Object lock = new Object();
        int[] arr = new Random().ints(1, 100).limit(10000).toArray();

        ExecutorService executorService =
                Executors.newFixedThreadPool(6);
        ExecutorService executorService1 =
                Executors.newWorkStealingPool();
        Callable<String> task1 = () -> {
            int temp=1;
            temp = arr[0];
            for(int i = 0; i<10000; i++) {
                if(arr[i]>temp){
                    Thread.sleep(1);
                    temp = arr[i];
                }
            }
            return String.valueOf(temp);
        };


        // Последовательная реализация функции поиска максимального элемента (вызов)
        Runtime runtime = Runtime.getRuntime();
        long m = System.currentTimeMillis();
        subsequence(arr);
        long allocatedMemory = runtime.totalMemory();
        System.out.println("Memory: " + (allocatedMemory - runtime.freeMemory() / 1024));
        System.out.println("Time: "+ (double) (System.currentTimeMillis() - m));
        space();

        // Вызов функции поиска максимального элемента с использованием многопоточности (FutureTask)
        m = System.currentTimeMillis();
        FutureTask<String> future = new FutureTask<>(task1);
        new Thread(future).start();
        try {
            System.out.println("FutureTask: "+future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        allocatedMemory = runtime.totalMemory();
        System.out.println("Memory: " + (allocatedMemory -runtime.freeMemory() / 1024));
        System.out.println("Time: "+ (double) (System.currentTimeMillis() - m));
        // executorService.shutdown();
        space();

        // Вызов функции поиска максимального элемента с использованием ForkJoin
        m = System.currentTimeMillis();
        Future<String> future2 = executorService.submit(task1);
        try {
            System.out.println("ForkJoin: "+future2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        allocatedMemory = runtime.totalMemory();
        System.out.println("Memory: " + (allocatedMemory -runtime.freeMemory() / 1024));
        System.out.println("Time: "+ (double) (System.currentTimeMillis() - m));
    }
}
