package concurrency.synchronization.practice.atomics;

import java.util.concurrent.atomic.AtomicInteger;

public class WebsiteAccessCounter {
  private static final AtomicInteger counter = new AtomicInteger(0);

  public static void access() {
    int visitNumber = counter.incrementAndGet(); // atomic increment
    System.out.println(Thread.currentThread().getName() + " truy cập lần thứ: " + visitNumber);
  }

  public static void main(String[] args) {
    Runnable userAccess = () -> {
      for (int i = 0; i < 5; i++) {
        access();
        try {
          Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
      }
    };

    for (int i = 0; i < 10; i++) {
      new Thread(userAccess, "User-" + i).start();
    }
  }
}
