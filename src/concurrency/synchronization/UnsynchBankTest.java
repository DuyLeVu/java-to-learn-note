package concurrency.synchronization;

import concurrency.synchronization.synch.Bank;

public class UnsynchBankTest {
public static final int ACCOUNTS = 1000;
public static final double INITIAL_BALANCE = 1000;
  public static final double MAX_AMOUNT = 1000;
  public static final int DELAY = 10;

  public static void main(String[] args) {
    var bank = new Bank(ACCOUNTS, INITIAL_BALANCE);
    for (int i = 0; i < ACCOUNTS; i ++) {
      int fromAccount = i;
      Runnable r = () -> {
        try {
          while (true) {
            int toAccount = (int) (bank.size() * Math.random());
            double amount = MAX_AMOUNT * Math.random();
            bank.transfer(fromAccount, toAccount, amount);
            Thread.sleep((int) (DELAY * Math.random()));
          }
        } catch (InterruptedException e) {
//          throw new RuntimeException(e);
        }
      };
      var t = new Thread(r);
      t.start();
    }
  }
}
