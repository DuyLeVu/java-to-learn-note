package concurrency.synchronization.practice.booking;

import java.util.concurrent.locks.ReentrantLock;

public class BookingSystemReentrantLock {
  private int ticketsAvailable = 100;
  private ReentrantLock lock = new ReentrantLock();

  public boolean book(String customer, int ticketRequest) {
    lock.lock();
    try {
      if (ticketRequest > ticketsAvailable) {
        System.out.println(Thread.currentThread().getName() + " ticket is not enough to book " + ticketRequest);
        return false;
      }
      ticketsAvailable -= ticketRequest;
      System.out.println(Thread.currentThread().getName() + " booked:" + ticketRequest);
//      Thread.sleep(1000);
      return true;
    } finally {
      lock.unlock();
    }
  }

  public int getTicketsAvailable() {
    return ticketsAvailable;
  }
}
