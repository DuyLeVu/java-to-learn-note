package vol1.generic;

public class CustomClass {
  private static int counter = 0;
  private final int id;

  public CustomClass() {
    id = ++counter;
  }

  @Override
  public String toString() {
    return "CustomClass#" + id;
  }
}
