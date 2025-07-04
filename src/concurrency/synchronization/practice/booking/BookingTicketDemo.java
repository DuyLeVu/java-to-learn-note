package concurrency.synchronization.practice.booking;

import java.util.concurrent.atomic.AtomicBoolean;

public class BookingTicketDemo {
  public static void main(String[] args) {
    var bookingSystem = new BookingTicket();
    AtomicBoolean status = new AtomicBoolean(true);
    Runnable r = () -> {
        status.set(bookingSystem.booking((int) (Math.random() * 5) + 1));

      };
    while (status.get()) {
      Thread t = new Thread(r);
      t.start();
    }
  }
}
