package concurrency.synchronization.practice.on_demand_initialization.config;

public class ConfigManagerDemo {
  public static void main(String[] args) {
    var config = ConfigManager.getInstance(); // Lần đầu tiên mới load
    System.out.println("HOST = " + config.get("host"));
    System.out.println("PORT = " + config.getInt("port"));

    var config2 = ConfigManager.getInstance(); // Không load lại
    System.out.println("TIMEOUT = " + config2.getLong("timeout"));
  }
}
