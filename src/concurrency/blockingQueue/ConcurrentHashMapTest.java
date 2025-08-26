package concurrency.blockingQueue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("Java", 1);
        map.put("Concurrency", 2);

//        Thread 1
        new Thread(() -> {
           for (int i = 0; i < 1000; i++) {
               map.put("T" + i, i);
           }
        }).start();

        // Thread 2
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(map.get("T" + i));
            }
        }).start();
    }
}
