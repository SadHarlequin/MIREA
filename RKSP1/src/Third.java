import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Third {
    public static void main(String[] args) {
        File dir1 = new File("C:\\Users\\Harlequin\\Documents\\temp");
        String name = "file";
        String ext = ".txt";

        int a = 0;
        int f =0;
        Queue<FutureTask<File>> que = new LinkedList<>();
        while(f<5) {
            int finalA = a;
            Callable<File> task1 = () -> {
                final int Finala = finalA;
                File new_f = new File(dir1, name+ Finala +ext);
                Thread.sleep(100+(int) (Math.random()*900));
                return new_f;
            };


            FutureTask<File> future = new FutureTask<>(task1);
            new Thread(future).start();
            que.add(future);
            a++;
            f++;

            try {
                Thread.sleep((int)new File(name+ a +ext).length()*7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(que.poll().get().getName());
                future.cancel(true);
                f--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
