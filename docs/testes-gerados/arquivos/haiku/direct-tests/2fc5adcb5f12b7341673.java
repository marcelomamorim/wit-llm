/*
 * @test
 * @summary Verify Math.addExact(int,int) returns the sum without overflow and throws ArithmeticException on overflow
 * @run main MathWitupTest
 */
public class MathWitupTest {
    public static void main(String[] args) {
        check(Math.addExact(1, 2) == 3, "1 + 2");
        check(Math.addExact(-5, 3) == -2, "-5 + 3");
        check(Math.addExact(0, 0) == 0, "0 + 0");
        check(Math.addExact(Integer.MAX_VALUE, 0) == Integer.MAX_VALUE, "MAX + 0");
        check(Math.addExact(Integer.MIN_VALUE, 0) == Integer.MIN_VALUE, "MIN + 0");
        check(Math.addExact(Integer.MIN_VALUE, Integer.MAX_VALUE) == -1, "MIN + MAX");
        check(Math.addExact(Integer.MAX_VALUE, -1) == Integer.MAX_VALUE - 1, "MAX + -1");

        expectOverflow(Integer.MAX_VALUE, 1);
        expectOverflow(Integer.MAX_VALUE, Integer.MAX_VALUE);
        expectOverflow(Integer.MIN_VALUE, -1);
        expectOverflow(Integer.MIN_VALUE, Integer.MIN_VALUE);
        expectOverflow(-1, Integer.MIN_VALUE);
    }

    static void expectOverflow(int x, int y) {
        try {
            Math.addExact(x, y);
        } catch (ArithmeticException e) {
            if (!"integer overflow".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for " + x + ", " + y + ": " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected overflow for " + x + ", " + y);
    }

    static void check(boolean condition, String description) {
        if (!condition) {
            throw new RuntimeException("Failed: " + description);
        }
    }
}
