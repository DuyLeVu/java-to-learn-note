package concurrency.synchronization.practice.booking;

import java.sql.SQLOutput;

public class BookingTicket {
  private int TICKETS_AVAILABLE = 100;

  public synchronized boolean booking(int ticketBooking) {
    try {
      if (TICKETS_AVAILABLE == 0) {
        throw new InterruptedException("het ve");
      }
      System.out.println(Thread.currentThread().getName() + " da dat:" + ticketBooking);
//      Thread.sleep(1000);
      TICKETS_AVAILABLE = TICKETS_AVAILABLE - ticketBooking;
      return true;
    } catch (InterruptedException exception) {
      System.out.println(Thread.currentThread().getName() + exception.getMessage());
      Thread.currentThread().interrupt();
      return false;
    }
  }
}
