/*
 * @test
 * @summary Verifies Math.incrementExact behavior for normal values and integer overflow
 * @run main MathWitupTest
 */

public class MathWitupTest {

    public static void main(String[] args) {
        check(Math.incrementExact(0) == 1);
        check(Math.incrementExact(-1) == 0);
        check(Math.incrementExact(41) == 42);
        check(Math.incrementExact(Integer.MAX_VALUE - 1) == Integer.MAX_VALUE);
        check(Math.incrementExact(Integer.MIN_VALUE) == Integer.MIN_VALUE + 1);

        boolean thrown = false;
        try {
            Math.incrementExact(Integer.MAX_VALUE);
        } catch (ArithmeticException e) {
            thrown = true;
            check("integer overflow".equals(e.getMessage()));
        }
        check(thrown);
    }

    private static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Assertion failed");
        }
    }
}
