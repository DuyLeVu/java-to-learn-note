package concurrency.synchronization.deadlocks;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {
  private final double[] accounts;
  private final Lock bankLock = new ReentrantLock();
  private final Condition sufficientFunds = bankLock.newCondition();

  public Bank(int n, double initialBalance) {
    accounts = new double[n];
    Arrays.fill(accounts, initialBalance);
  }

  public void transfer(int from, int to, double amount) throws InterruptedException {
    bankLock.lock();
    try {
      while (accounts[from] < amount) {
        System.out.println(Thread.currentThread().getName() + ": chờ vì không đủ tiền từ account " + from);
        sufficientFunds.await();
      }
      accounts[from] -= amount;
      accounts[to] += amount;
      System.out.printf("%s: %10.2f from %d to %d. Total: %10.2f\n",
          Thread.currentThread().getName(), amount, from, to, getTotalBalance());
      sufficientFunds.signalAll(); // Đảm bảo tất cả thread có cơ hội tiếp tục
    } finally {
      bankLock.unlock();
    }
  }

  public double getTotalBalance() {
    double sum = 0;
    for (double a : accounts) sum += a;
    return sum;
  }

  public int size() {
    return accounts.length;
  }
}

class TransferRunnable implements Runnable {
  private final Bank bank;
  private final int fromAccount;
  private final double maxAmount;
  private final int DELAY = 10;

  public TransferRunnable(Bank b, int from, double max) {
    bank = b;
    fromAccount = from;
    maxAmount = max;
  }

  public void run() {
    try {
      while (true) {
        int toAccount = (int) (bank.size() * Math.random());
        double amount = maxAmount * Math.random();
        bank.transfer(fromAccount, toAccount, amount);
        Thread.sleep((int) (DELAY * Math.random()));
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

public class DeadlockSimulation1 {
  public static final int NACCOUNTS = 10;
  public static final double INITIAL_BALANCE = 1000;

  public static void main(String[] args) {
    Bank bank = new Bank(NACCOUNTS, INITIAL_BALANCE);

    for (int i = 0; i < NACCOUNTS; i++) {
      Thread t = new Thread(new TransferRunnable(bank, i, 2 * INITIAL_BALANCE)); // Đầy mức giao dịch cao để gây deadlock
      t.start();
    }
  }
}
