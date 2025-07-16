package concurrency.synchronization.practice.on_demand_initialization.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
  private final Properties properties = new Properties();

  private ConfigManager() {
    System.out.println("Loading config for the first time...");
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
      if (input == null) {
        throw new RuntimeException("Cannot find config.properties file!");
      }
      properties.load(input);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load configuration", e);
    }
  }

  // Holder class – JVM đảm bảo thread-safe lazy-loading
  private static class Holder {
    private static final ConfigManager INSTANCE = new ConfigManager();
  }

  // Global access point
  public static ConfigManager getInstance() {
    return Holder.INSTANCE;
  }

  // API đọc cấu hình
  public String get(String key) {
    return properties.getProperty(key);
  }

  public int getInt(String key) {
    return Integer.parseInt(properties.getProperty(key));
  }

  public long getLong(String key) {
    return Long.parseLong(properties.getProperty(key));
  }
}
