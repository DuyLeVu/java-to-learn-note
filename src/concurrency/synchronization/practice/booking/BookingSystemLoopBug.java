package concurrency.synchronization.practice.booking;

public class BookingSystemLoopBug {
  private int ticketsAvailable = 10;

  public synchronized boolean book(int ticketRequested) {
    if (ticketsAvailable >= ticketRequested) {
      System.out.println(Thread.currentThread().getName() + " booked: " + ticketRequested);
      ticketsAvailable -= ticketRequested;
      return true;
    } else {
      System.out.println(Thread.currentThread().getName() + " failed to book " + ticketRequested + ". Only " + ticketsAvailable + " left.");
      return false;
    }
  }

  public static void main(String[] args) {
    BookingSystemLoopBug bookingSystemLoopBug = new BookingSystemLoopBug();

    Runnable task = () -> {
      while (true) {
        int tickets = (int) (Math.random() * 3) + 1;
        var status = bookingSystemLoopBug.book(tickets);
        try {
          Thread.sleep(100); // Chậm lại 1 chút để dễ nhìn
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
