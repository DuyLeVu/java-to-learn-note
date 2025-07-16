package concurrency.introduce;

public class InterruptibleTaskManager {
  private Thread workerThread;
  private volatile boolean running = false;

  public static void main(String[] args) throws InterruptedException {
    InterruptibleTaskManager manager = new InterruptibleTaskManager();

    manager.start();
    Thread.sleep(4000);
    manager.stop();
  }

  public void start() {
    if (workerThread != null && workerThread.isAlive()) {
      System.out.println("Task đã được chạy trước đó.");
      return;
    }

    running = true;

    workerThread = new Thread(() -> {
      try {
        System.out.println("Bắt đầu task...");
        while (running) {
          doWork();
          Thread.sleep(1000);
        }
      } catch (InterruptedException e) {
        System.out.println("Task bị Interrup khi đang sleep hoặc chờ.");
        Thread.currentThread().interrupt();
      } finally {
        cleanup();
      }
      System.out.println("Task đã dừng.");
    });

    workerThread.start();
  }

  public void stop() {
    if (workerThread != null) {
      System.out.println("Gửi tín hiệu dừng...");
      running = false;
      workerThread.interrupt();
    }
  }

  public boolean isRunning() {
    return workerThread != null && workerThread.isAlive();
  }

  private void doWork() {
    System.out.println("🛠️ Đang thực hiện công việc...");
    // Ở đây có thể thêm logic xử lý thực tế, ví dụ: đọc file, xử lý dữ liệu,...
  }

  private void cleanup() {
    System.out.println("🧹 Dọn dẹp tài nguyên...");
    // Đóng file, giải phóng bộ nhớ,... nếu cần
  }
}
