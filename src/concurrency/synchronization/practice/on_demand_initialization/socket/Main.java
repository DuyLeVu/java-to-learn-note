package concurrency.synchronization.practice.on_demand_initialization.socket;

public class Main {
  public static void main(String[] args) {
    // Lần đầu: mở kết nối
    SocketConnectionManager conn = SocketConnectionManager.getInstance();
    conn.send("Hello from Main Thread");

    // Có thể gọi lại nhiều lần từ các luồng khác
    Runnable r = () -> {
      var connection = SocketConnectionManager.getInstance();
      connection.send("Hello from " + Thread.currentThread().getName());
    };
    for (int i = 0; i < 3; i++) {
      new Thread(r, "Thread-" + i).start();
    }

    // Tùy chọn đóng khi xong
    // conn.close();
  }
}
