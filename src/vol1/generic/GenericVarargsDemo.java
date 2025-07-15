package vol1.generic;

import java.util.Arrays;

public class GenericVarargsDemo {
  @SafeVarargs
  public static <E> E[] array(E... elements) {
    return elements;
  }

  public static void main(String[] args) {
    Pair<String> p1 = new Pair<>("One", "1");
    Pair<String> p2 = new Pair<>("Two", "2");

    Pair<String>[] table = array(p1, p2);

    // In ra để kiểm tra ban đầu
    System.out.println("Trước khi bị sửa: " + Arrays.toString(table));

    // Gán mảng sang Object[] rồi chèn sai kiểu vào
    Object[] objArray = table;
    objArray[0] = new Pair<Integer>(100, 200); // ⚠️ Không lỗi khi compile
    // Truy cập phần tử 0 và ép kiểu
    System.out.println("Sau khi bị sửa: " + table[0]); // 💥 Crash ở đây
  }
}

class Pair<T> {
  private T first;
  private T second;

  public Pair(T first, T second) {
    this.first = first;
    this.second = second;
  }

  public String toString() {
    return "(" + first + ", " + second + ")";
  }
}