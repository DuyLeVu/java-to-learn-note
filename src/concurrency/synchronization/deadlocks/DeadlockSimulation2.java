package concurrency.synchronization.deadlocks;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockSimulation2 {
  public static final int NACCOUNTS = 10;
  public static final double INITIAL_BALANCE = 1000;
  private static final double MAX_TRANSFER = 2000;

  public static void main(String[] args) {
    DeadlockBank bank = new DeadlockBank(NACCOUNTS, INITIAL_BALANCE);

    for (int i = 0; i < NACCOUNTS; i++) {
      final int accountId = i;
      var t = new Thread(() -> {
        try {
          while (true) {
            int from = (int) (bank.size() * Math.random());
            double amount = MAX_TRANSFER * Math.random();
            bank.transfer(from, accountId, amount); // ❗️Đảo chiều from ↔ to
            Thread.sleep((int) (Math.random() * 100));
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      });
      t.start();
    }
    // Let it run for a few seconds, then print balances
    new Thread(() -> {
      try {
        Thread.sleep(5000);
        System.out.println("\n🧾 Final account balances:");
        double[] snapshot = bank.getAccountsSnapshot();
        for (int i = 0; i < snapshot.length; i++) {
          System.out.printf("Account %d: %.2f%n", i, snapshot[i]);
        }
        System.out.printf("🔢 Total: %.2f%n", bank.getTotalBalance());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }
}

class DeadlockBank {
  private final double[] accounts;
  private final Lock bankLock = new ReentrantLock();
  private final Condition sufficientFunds = bankLock.newCondition();

  public DeadlockBank(int n, double initialBalance) {
    accounts = new double[n];
    Arrays.fill(accounts, initialBalance);
  }

  public void transfer(int from, int to, double amount) throws InterruptedException {
    bankLock.lock();
    try {
      while (accounts[from] < amount) {
        System.out.printf("⚠️ %s waits: Account %d has %.2f < %.2f%n",
            Thread.currentThread().getName(), from, accounts[from], amount);
        sufficientFunds.await();
      }
      accounts[from] -= amount;
      accounts[to] += amount;
      System.out.printf("✅ %s transfers %.2f from %d to %d. Total: %.2f%n",
          Thread.currentThread().getName(), amount, from, to, getTotalBalance());
      sufficientFunds.signalAll(); // Notify all waiting threads
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

  public double[] getAccountsSnapshot() {
    return accounts.clone();
  }
}
