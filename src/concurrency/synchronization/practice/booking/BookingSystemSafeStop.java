package concurrency.synchronization.practice.booking;

import java.util.concurrent.atomic.AtomicInteger;

public class BookingSystemSafeStop {
  private final AtomicInteger ticketsAvailable = new AtomicInteger(10);
  private volatile boolean soldOut = false;

  public synchronized boolean book(int ticketsRequest) {
    var available = ticketsAvailable.get();
    if (available >= ticketsRequest) {
      System.out.println(Thread.currentThread().getName() + " booked: " + ticketsRequest);
      ticketsAvailable.addAndGet(-ticketsRequest);
      return true;
    } else {
      System.out.println(Thread.currentThread().getName() + " failed to book: " + ticketsRequest + ".Only " + available + " left.");
      return false;
    }
  }

  public boolean isSoldOut() {
    return ticketsAvailable.get() <= 0;
  }

  public static void main(String[] args) {
    BookingSystemSafeStop booking = new BookingSystemSafeStop();

    Runnable task = () -> {
      while (!booking.soldOut) {
        int ticket = (int) (Math.random() * 3) + 1;
        boolean success = booking.book(ticket);

        if (booking.isSoldOut()) {
          booking.soldOut = true; // Gửi tín hiệu dừng tới toàn bộ thread
          System.out.println("===> Vé đã hết. " + Thread.currentThread().getName() + " thông báo dừng hệ thống.");
          break;
        }
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    };

    // Tạo 3 thread đặt vé
    for (int i = 0; i < 3; i++) {
      new Thread(task, "Thread-" + i).start();
    }
  }
}
