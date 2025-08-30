package concurrency.threadSafeCollections.chm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class ApiAnalyticsDemo {
    private final ConcurrentHashMap<String, LongAdder> counters = new ConcurrentHashMap<>();

    // Ghi nhận một lần gọi endpoint (nhiều thread cùng gọi)
    public void  hit(String endpoint) {
        counters.computeIfAbsent(endpoint, k -> new LongAdder()).increment();
    }

    // Chạy các bulk operations để phân tích nhanh
    public void report(long threshold) {
        // 1) Tìm (bất kỳ) endpoint đầu tiên > 1000 hits (xấp xỉ, dừng ngay khi gặp)
        String firstHot = counters.search(threshold, (k, v) ->
            v.sum() > 1000 ? k : null);
        System.out.println("First hotspot (≈): " + firstHot);

        // 2) In ra các endpoint lớn hơn 1000 theo định dạng "k -> v"
        //    Dùng transformer như filter: trả null thì bỏ qua
        counters.forEach(threshold, (k, v) -> v.sum() > 1000 ? k + " -> " + v.sum() : null, System.out::println);

        // 3) Tổng số request (primitive specialization để tránh boxing)
        long total = counters.reduceValuesToLong(
                threshold,
                LongAdder::sum, // transformer: LongAdder -> long
                0L,             // neutral element cho cộng
                Long::sum);     // accumulator (primitive)
        System.out.println("Total hits: " + total);

        // 4) Đếm số endpoint có lưu lượng > 1000 (dùng object reduce + filter)
        Long heavyCount = counters.reduceValues(
                threshold,
                v -> v.sum() > 1000 ? 1L : null, // transformer & filter
                Long::sum);                       // accumulator
        System.out.println("Endpoints >1000: " + (heavyCount == null ? 0 : heavyCount));

        // 5) Tìm endpoint “nóng” nhất (reduce trên entries)
//        Entry<String, LongAdder> hottest = counters.reduceEntries(
//                threshold,
//                (e1, e2) -> e1.getValue().sum() >= e2.getValue().sum() ? e1 : e2);
//        System.out.println("Hottest endpoint: " +
//                (hottest == null ? null : hottest.getKey() + " -> " + hottest.getValue().sum()));

        // 6) Ví dụ reduceKeys: độ dài key lớn nhất (chỉ để minh họa)
        Integer maxKeyLen = counters.reduceKeys(threshold, String::length, Integer::max);
        System.out.println("Max endpoint name length: " + maxKeyLen);
    }

    public static void main(String[] args) {
        ApiAnalyticsDemo analytics = new ApiAnalyticsDemo();
// nhiều thread gọi:
        analytics.hit("/orders/create");
        analytics.hit("/orders/create");
        analytics.hit("/users/me");
// ...
// bulk ops chạy song song mạnh mẽ:
        analytics.report(1L); // threshold=1 → tận dụng tối đa parallelism
    }
}


