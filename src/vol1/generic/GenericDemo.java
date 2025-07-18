package vol1.generic;

public class GenericDemo {
  public static void main(String[] args) {

    Pair<String> p = Pair.makePair2(String.class);
//    String.class là đối tượng Class<String>.
//    Java sẽ tìm constructor không tham số của String rồi gọi newInstance() hai lần.
    System.out.println(p); // Output: ("", "")

    Pair<CustomClass> customPair = Pair.makePair2(CustomClass.class);
    System.out.println("Pair<CustomClass>: " + customPair);
  }

}
