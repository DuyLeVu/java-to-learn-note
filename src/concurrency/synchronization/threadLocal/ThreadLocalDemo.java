package concurrency.synchronization.threadLocal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalDemo {
  private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

  public static void main(String[] args) {
    Runnable r = () -> {
      String formatted = formatter.get().format(new Date());
      System.out.println(Thread.currentThread().getName() + ": " + formatted);
    };

    for (int i = 0; i < 3; i++) {
      new Thread(r, "Thread-" + i).start();
    }
  }
}
