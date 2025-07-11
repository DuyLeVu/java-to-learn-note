package concurrency.synchronization.practice.atomics;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogServiceWithQueue {
  private static final Queue<String> logs = new ConcurrentLinkedQueue<>();

  public static void log(String message) {
    logs.offer(Thread.currentThread().getName() + " - " + message);
  }

  public static void printLogs() {
    logs.forEach(System.out::println);
  }

  public static void main(String[] args) throws InterruptedException {
    Runnable logTask = () -> {
      for (int i = 0; i < 5; i++) {
        log("access lần " + i);
      }
    };

    Thread[] threads = new Thread[5];
    for (int i = 0; i < 5; i++) {
      threads[i] = new Thread(logTask, "LogThread-" + i);
      threads[i].start();
    }

    for (Thread t : threads) t.join();

    System.out.println("📋 Tất cả log:");
    printLogs();
  }
}
