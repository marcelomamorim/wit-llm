/*
 * @test
 * @summary Verifies Math.decrementExact(int) returns a - 1 and throws ArithmeticException on integer underflow
 * @run main MathWitupTest
 */

public class MathWitupTest {

    public static void main(String[] args) {
        check(Math.decrementExact(5) == 4, "decrementExact(5) should be 4");
        check(Math.decrementExact(1) == 0, "decrementExact(1) should be 0");
        check(Math.decrementExact(0) == -1, "decrementExact(0) should be -1");
        check(Math.decrementExact(-1) == -2, "decrementExact(-1) should be -2");
        check(Math.decrementExact(Integer.MIN_VALUE + 1) == Integer.MIN_VALUE,
              "decrementExact(MIN_VALUE + 1) should be MIN_VALUE");
        check(Math.decrementExact(Integer.MAX_VALUE) == Integer.MAX_VALUE - 1,
              "decrementExact(MAX_VALUE) should be MAX_VALUE - 1");

        expectOverflow();
    }

    static void expectOverflow() {
        try {
            Math.decrementExact(Integer.MIN_VALUE);
        } catch (ArithmeticException e) {
            check("integer overflow".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
            return;
        }
        throw new RuntimeException("decrementExact(MIN_VALUE) did not throw ArithmeticException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
