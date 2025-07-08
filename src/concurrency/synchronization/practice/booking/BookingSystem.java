package concurrency.synchronization.practice.booking;

public class BookingSystem {
  private int ticketsAvailable = 100;

  public synchronized boolean book(String customer, int ticketRequest) {
    if (ticketRequest > ticketsAvailable) {
      System.out.println(Thread.currentThread().getName() + " ticket is not enough to book " + ticketRequest);
      return false;
    }
    ticketsAvailable -= ticketRequest;
    System.out.println(Thread.currentThread().getName() + " booked:" + ticketRequest);
//      Thread.sleep(1000);
    return true;
  }

  public int getTicketsAvailable() {
    return ticketsAvailable;
  }
}
