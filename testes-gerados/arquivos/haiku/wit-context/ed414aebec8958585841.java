/*
 * @test
 * @summary Verify Math.negateExact(int) returns the negation and throws ArithmeticException on overflow
 * @run main MathWitupTest
 */

public class MathWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testNormalValues() {
        check(Math.negateExact(0) == 0, "negateExact(0) should be 0");
        check(Math.negateExact(1) == -1, "negateExact(1) should be -1");
        check(Math.negateExact(-1) == 1, "negateExact(-1) should be 1");
        check(Math.negateExact(42) == -42, "negateExact(42) should be -42");
        check(Math.negateExact(-42) == 42, "negateExact(-42) should be 42");
        check(Math.negateExact(Integer.MAX_VALUE) == -Integer.MAX_VALUE,
              "negateExact(MAX_VALUE) should be -MAX_VALUE");
        check(Math.negateExact(-Integer.MAX_VALUE) == Integer.MAX_VALUE,
              "negateExact(-MAX_VALUE) should be MAX_VALUE");
    }

    static void testOverflowThrows() {
        boolean thrown = false;
        try {
            Math.negateExact(Integer.MIN_VALUE);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "negateExact(MIN_VALUE) should throw ArithmeticException");
    }

    public static void main(String[] args) {
        testNormalValues();
        testOverflowThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
