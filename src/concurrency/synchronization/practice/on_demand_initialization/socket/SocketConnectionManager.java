package concurrency.synchronization.practice.on_demand_initialization.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Ví dụ Singleton nâng cao theo mô hình On-Demand Initialization dùng để mở kết nối mạng TCP socket tới một server
 * (ví dụ localhost:9999), và đảm bảo rằng chỉ mở kết nối duy nhất một lần, bất kể có bao nhiêu thread sử dụng.
 */
public class SocketConnectionManager {
  private final Socket socket;
  private final OutputStream output;

  // Khởi tạo kết nối khi được gọi lần đầu
  private SocketConnectionManager() {
    System.out.println("Opening socket connection to localhost:9999...");
    try {
      this.socket = new Socket("localhost", 9999);
      this.output = socket.getOutputStream();
    } catch (IOException ex) {
      throw new RuntimeException("Could not open socket connection", ex);
    }
  }

  // Global access point
  public static SocketConnectionManager getInstance() {
    return Holder.INSTANCE;
  }

  // Gửi message tới server
  public void send(String message) {
    try {
      output.write((message + "\n").getBytes());
      output.flush();
    } catch (IOException e) {
      throw new RuntimeException("Failed to send data", e);
    }
  }

  // Đóng socket nếu cần
  public void close() {
    try {
      socket.close();
    } catch (IOException e) {
      System.err.println("Failed to close socket: " + e.getMessage());
    }
  }

  // Holder đảm bảo lazy-load + thread-safe
  private static class Holder {
    private static final SocketConnectionManager INSTANCE = new SocketConnectionManager();
  }
}
