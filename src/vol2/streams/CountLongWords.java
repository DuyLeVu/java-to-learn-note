package vol2.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CountLongWords {
  public static void main(String[] args) throws IOException {
    var contents = Files.readString(Path.of("note.txt"));
    List<String> words = List.of(contents.split(" "));
    long count = 0;
    var start = System.nanoTime();
    for (String w : words) {
      if (w.length() > 12) count++;
    }
    var end = System.nanoTime();
    System.out.println("Count " + count + " words has length > 12 by for loop in " + (end - start));
    start = System.nanoTime();
    count = words.stream().filter(w -> w.length() > 12).count();
    end = System.nanoTime();
    System.out.println("Count " + count + " words has length > 12 by Stream in " + (end - start));
    start = System.nanoTime();
    count = words.parallelStream().filter(w -> w.length() > 12).count();
    end = System.nanoTime();
    System.out.println("Count " + count + " words has length > 12 by Parallel Stream in " + (end - start));
  }
}
