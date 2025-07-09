package concurrency.synchronization.final_variables;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafeFinalMapDemo {
  // final map dùng ConcurrentHashMap để tránh lỗi ClassCastException và đảm bảo thread-safe
  private final Map<String, Integer> sharedMap = new ConcurrentHashMap<>();

  public static void main(String[] args) throws InterruptedException {
    SafeFinalMapDemo demo = new SafeFinalMapDemo();

    int numberOfWriters = 5;
    int operationsPerWriter = 1000;

    // Thread pool cố định để quản lý thread writer
    ExecutorService executor = Executors.newFixedThreadPool(numberOfWriters);

    CountDownLatch latch = new CountDownLatch(numberOfWriters);

    for (int i = 0; i < numberOfWriters; i++) {
      int writerId = i;
      executor.submit(() -> {
        for (int j = 0; j < operationsPerWriter; j++) {
          String key = "Writer-" + writerId + "-Entry-" + j;
          demo.addToMap(key, j);
        }
        latch.countDown();
      });
    }

    // Đợi cho đến khi tất cả writer hoàn tất
    latch.await();
    executor.shutdown();

    // In ra thống kê kết quả
    System.out.println("Tổng số phần tử trong sharedMap: " + demo.sharedMap.size());

    // Tùy chọn: kiểm tra một số entry
    demo.sharedMap.entrySet().stream()
        .limit(10)
        .forEach(entry -> System.out.println(entry.getKey() + " = " + entry.getValue()));
  }

  // phương thức ghi dữ liệu vào Map
  public void addToMap(String key, int value) {
    sharedMap.put(key, value);
  }
}
