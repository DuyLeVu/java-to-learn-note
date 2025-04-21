package chapter_6;

public class LambdaToLearn {
    //    Bài 4: Xử lý chuỗi với transform()
    String input = " 12345 ";
    int number = input.strip().transform(Integer::parseInt);
}
