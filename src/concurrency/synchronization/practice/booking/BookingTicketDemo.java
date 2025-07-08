package concurrency.synchronization.practice.booking;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BookingTicketDemo {
  public static void main(String[] args) {
//    var bookingSystem = new BookingSystem();
//    for (int i = 0; i < 100; i++) {
//      var customer = "Customer-" + i;
//      var tickets = (int) (Math.random() * 5) + 1;
//      new Thread(() -> bookingSystem.book(tickets)).start();
//    }

//  Demo with ExecutorService
    var bookingSystem = new BookingSystemReentrantLock();
  ExecutorService executor = Executors.newFixedThreadPool(5);
    // Giả lập 20 khách hàng đặt vé
    for (int i = 1; i <= 20; i++) {
      var customer = "Customer-" + i;
      int ticketsToBook = (int) (Math.random() * 5) + 1;
      Runnable task = () -> {bookingSystem.book(customer, ticketsToBook);};
      executor.submit(task);
    }

    // Đóng ExecutorService
    executor.shutdown();
    try {
      if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }
}
