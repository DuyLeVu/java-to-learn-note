package concurrency.introduce;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiTaskManager {
//  Quản lý tất cả các task
  private final Map<String, TaskRunner> taskMap = new ConcurrentHashMap<>();

  private static class TaskRunner implements Runnable {
    private final String taskId;
    private final int delayMs;
    private final Thread thread;
    private volatile boolean running = true;

    public TaskRunner(String taskId, int delayMs) {
      this.taskId = taskId;
      this.delayMs = delayMs;
      this.thread = new Thread(this, "Task-"+ taskId);
    }

    public void start() {
      thread.start();
    }

    public void stop() {
      running = false;
      thread.interrupt();
    }

    public boolean isAlive() {
      return thread.isAlive();
    }

    @Override
    public void run() {
      try {
        System.out.println("Bắt đầu task: " + taskId);
        while (running) {
          System.out.println("[" + taskId + "] Đang xử lý...");
          Thread.sleep(delayMs);
        }
      } catch (InterruptedException e) {
        System.out.println("[" + taskId + "] Bị interrupt.");
        Thread.currentThread().interrupt();
      } finally {
        System.out.println("[" + taskId + "] Dừng và dọn dẹp.");
      }
    }
  }

  public void startTask(String taskId, int delayMs) {
    if (taskMap.containsKey(taskId)) {
      System.out.println("[" + taskId + "] Đã tồn tại");
      return;
    }

    TaskRunner task = new TaskRunner(taskId, delayMs);
    taskMap.put(taskId, task);
    task.start();
  }

  // Dừng task theo ID
  public void stopTask(String taskId) {
    TaskRunner task = taskMap.remove(taskId);
    if (task != null) {
      task.stop();
    } else {
      System.out.println("Không tìm thấy task " + taskId);
    }
  }

  // Dừng toàn bộ task
  public void stopAllTasks() {
    for (String id : taskMap.keySet()) {
      stopTask(id);
    }
  }

  // Kiểm tra trạng thái task
  public void printTaskStatus() {
    taskMap.forEach((id, task) -> {
      System.out.println("Task " + id + ": " + (task.isAlive() ? "ĐANG CHẠY" : "ĐÃ DỪNG"));
    });
  }

  // Demo
  public static void main(String[] args) throws InterruptedException {
    MultiTaskManager manager = new MultiTaskManager();
    manager.startTask("A", 1000);
    manager.startTask("B", 1500);
    manager.startTask("C", 2000);

    Thread.sleep(5000);

    manager.stopTask("B");
    Thread.sleep(3000);

    manager.stopAllTasks();
    Thread.sleep(2000);

    manager.printTaskStatus();
  }
}
