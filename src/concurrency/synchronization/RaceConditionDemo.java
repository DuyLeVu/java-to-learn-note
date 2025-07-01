package concurrency.synchronization;

class UnsafeBank {
  private int balance = 100;

  public void withdraw(String name, int amount) {

    if (balance >= amount) {
      System.out.println(name +": " );
      System.out.println(name + " đang rút " + amount);
      balance -= amount;
      System.out.println("✅ Số dư sau khi " + name + " rút: " + balance);
    } else {
      System.out.println("❌ " + name + " không đủ tiền.");
    }
  }
}

public class RaceConditionDemo {
  public static void main(String[] args) {
    UnsafeBank bank = new UnsafeBank();

    Runnable r = () -> {
      for (int i = 0; i < 5; i++) {
        bank.withdraw(Thread.currentThread().getName(), 30);
      }
    };

    Thread t1 = new Thread(r, "Alice");
    Thread t2 = new Thread(r, "Bob");

    t1.start();
    t2.start();
  }
}

