package vol1.generic;

import java.util.function.IntFunction;

public class ArrayAlg {
    public static <T extends Comparable> T[] minmax(IntFunction<T[]> constr, T... a) {
      T[] result = constr.apply(2);
      if (a.length == 0) return result;

      T min = a[0], max = a[0];
      for (T element : a) {
        if (element.compareTo(min) < 0) min = element;
        if (element.compareTo(max) > 0) max = element;
      }
      result[0] = min;
      result[1] = max;
      return result;
    }

    public static void main(String[] args) {
      String[] names = ArrayAlg.minmax(String[]::new, "Tom", "Dick", "Harry");
      System.out.println("Min = " + names[0]);
      System.out.println("Max = " + names[1]);
    }
}
