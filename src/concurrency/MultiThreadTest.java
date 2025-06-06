package concurrency;

public class MultiThreadTest {
  public static void main(String[] args) {
//    var count = 0;
    Runnable r = () -> {
      try {
        var count = 0;
        while (count < 10) {
        System.out.println(Thread.currentThread() + "Ping");
          count ++;
        Thread.sleep(5000);
        }
      } catch (InterruptedException e) {

      }
    };
    Runnable r2 = () -> {
      try {
        var count = 0;
        while (count < 10) {
        System.out.println(Thread.currentThread() + "Pong");
          count ++;
        Thread.sleep(7000);
        }
      } catch (InterruptedException e) {

      }
    };
    var t = new Thread(r);
    var t2 = new Thread(r2);

    t.start();
    t2.start();
  }
}
