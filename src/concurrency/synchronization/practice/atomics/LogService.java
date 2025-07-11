package concurrency.synchronization.practice.atomics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogService {
  private static final List<String> logs = Collections.synchronizedList(new ArrayList<>());

  public static void log(String message) {
    logs.add(Thread.currentThread().getName() + " - " + message);
  }

  public static void printLogs() {
    logs.forEach(System.out::println);
  }

  public static void main(String[] args) throws InterruptedException {
    Runnable logTask = () -> {
      for (int i = 0; i < 5; i++) {
        log("truy cập lần " + i);
      }
    };

    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Thread t = new Thread(logTask, "Logger-" + i);
      threads.add(t);
      t.start();
    }

    for (Thread t : threads) {
      t.join();
    }

    System.out.println("Toàn bộ log:");
    printLogs();
  }
}
