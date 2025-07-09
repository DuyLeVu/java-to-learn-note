package concurrency.synchronization.final_variables;

import java.util.HashMap;
import java.util.Map;

public class UnSafeFinalMapDemo {
  private final Map<String, Integer> sharedMap = new HashMap<>();

  public void addToMap(String key, int value) {
    sharedMap.put(key, value); // NOT THREAD-SAFE!
  }

  public Integer getFromMap(String key) {
    return sharedMap.get(key);
  }

  public static void main(String[] args) {
    UnSafeFinalMapDemo demo = new UnSafeFinalMapDemo();

    Runnable writer = () -> {
      for (int i = 0; i < 1000; i++) {
        demo.addToMap(Thread.currentThread().getName() + "-" + i, i);
      }
    };

    // Start 5 threads writing concurrently
    for (int i = 0; i < 5; i++) {
      new Thread(writer, "Writer-" + i).start();
    }

    // Also read concurrently
    Runnable reader = () -> {
      for (int i = 0; i < 1000; i++) {
        demo.getFromMap("Writer-1-" + i); // May return null or wrong value
      }
    };

    new Thread(reader).start();
  }
}
