/*
 * @test
 * @summary Tests Math.negateExact(long) for normal values and for the Long.MIN_VALUE overflow case
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        check(Math.negateExact(5L) == -5L, "negateExact(5) should be -5");
        check(Math.negateExact(-5L) == 5L, "negateExact(-5) should be 5");
        check(Math.negateExact(0L) == 0L, "negateExact(0) should be 0");
        check(Math.negateExact(Long.MAX_VALUE) == -Long.MAX_VALUE, "negateExact(MAX_VALUE) should be -MAX_VALUE");
        check(Math.negateExact(Long.MIN_VALUE + 1L) == Long.MAX_VALUE, "negateExact(MIN_VALUE + 1) should be MAX_VALUE");

        boolean thrown = false;
        try {
            Math.negateExact(Long.MIN_VALUE);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "negateExact(Long.MIN_VALUE) should throw ArithmeticException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
