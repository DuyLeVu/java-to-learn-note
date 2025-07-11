package concurrency.synchronization.atomics;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo {
  private static volatile int count = 0;
  private static Runnable r = () -> {
    for (int i = 0; i < 50000; i++) {
      count++;
    }
  };

  private static final AtomicInteger count2 = new AtomicInteger(0);

  private static final Runnable r2 = () -> {
    for (int i = 0; i < 50000; i++) {
      count2.incrementAndGet(); // ✅ Atomic - thread-safe
    }
  };

  public static void main(String[] args) throws InterruptedException {
    Thread[] threads = new Thread[4];

    // Group 1: count with volatile
    for (int i = 0; i < 2; i++) {
      threads[i] = new Thread(Demo.r);
      threads[i].start();
    }

    // Group 2: count with atomic
    for (int i = 2; i < 4; i++) {
      threads[i] = new Thread(Demo.r2);
      threads[i].start();
    }

    // Wait for all threads
    for (var t : threads) {
      t.join();
    }

    System.out.println("✅ Count by volatile: " + Demo.count);
    System.out.println("✅ Count by atomic: " + Demo.count2);
  }

}
