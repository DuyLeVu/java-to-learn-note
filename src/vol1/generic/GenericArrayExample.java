package vol1.generic;

import java.lang.reflect.Array;

public class GenericArrayExample {
  public static void main(String[] args) {
    Integer[] numbers = {3, 8, 2, 9, 1};
    Integer[] result = minmax(Integer.class, numbers);
    System.out.println("Min: " + result[0]);
    System.out.println("Max: " + result[1]);
  }

  public static <T extends Comparable<T>> T[] minmax(Class<T> clazz, T... values) {
    if (values == null || values.length == 0) return null;

    T min = values[0];
    T max = values[0];
    for (T v : values) {
      if (v.compareTo(min) < 0) min = v;
      if (v.compareTo(max) > 0) max = v;
    }

    // Tạo mảng thông qua java.lang.reflect.Array
    @SuppressWarnings("unchecked")
    T[] result = (T[]) Array.newInstance(clazz, 2);
    result[0] = min;
    result[1] = max;
    return result;
  }
}
