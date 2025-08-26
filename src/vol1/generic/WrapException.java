package vol1.generic;

public class WrapException {
    public static void main(String[] args) {
        try {
            MyException custom = new MyException("Custom error");
            doWork(custom);
        } catch (MyException e) {
            System.out.println("Caught: " + e.getMessage());
            System.out.println("Cause: " + e.getCause());
        }
    }

    public static <T extends Throwable> void doWork(T t) throws T {
        try {
            // giả sử có lỗi xảy ra
            throw new RuntimeException("Something failed");
        } catch (Throwable realCause) {
            t.initCause(realCause); // gán nguyên nhân thực sự
            throw t; // ✅ có thể ném ra lại biến t kiểu T
        }
    }
}

class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}
