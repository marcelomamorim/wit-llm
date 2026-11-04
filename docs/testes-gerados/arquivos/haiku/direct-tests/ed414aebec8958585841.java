/*
 * @test
 * @summary Witup-style tests for Math.negateExact(int)
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

    static void testRegularValues() {
        check(Math.negateExact(5) == -5, "negateExact(5) should be -5");
        check(Math.negateExact(-5) == 5, "negateExact(-5) should be 5");
        check(Math.negateExact(0) == 0, "negateExact(0) should be 0");
        check(Math.negateExact(1) == -1, "negateExact(1) should be -1");
        check(Math.negateExact(-1) == 1, "negateExact(-1) should be 1");
    }

    static void testBoundaryValues() {
        check(Math.negateExact(Integer.MAX_VALUE) == -Integer.MAX_VALUE,
              "negateExact(MAX_VALUE) should be -MAX_VALUE");
        check(Math.negateExact(-Integer.MAX_VALUE) == Integer.MAX_VALUE,
              "negateExact(-MAX_VALUE) should be MAX_VALUE");
        check(Math.negateExact(Integer.MIN_VALUE + 1) == Integer.MAX_VALUE,
              "negateExact(MIN_VALUE + 1) should be MAX_VALUE");
    }

    static void testOverflowThrows() {
        boolean thrown = false;
        try {
            Math.negateExact(Integer.MIN_VALUE);
        } catch (ArithmeticException e) {
            thrown = true;
            check("integer overflow".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "negateExact(MIN_VALUE) should throw ArithmeticException");
    }

    public static void main(String[] args) {
        testRegularValues();
        testBoundaryValues();
        testOverflowThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
