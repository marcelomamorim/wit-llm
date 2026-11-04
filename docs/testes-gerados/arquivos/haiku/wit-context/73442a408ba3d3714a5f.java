/*
 * @test
 * @summary Verifies Math.negateExact(long) overflow behavior and normal results
 * @run main MathWitupTest
 */

public class MathWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        testMinValueThrowsArithmeticException();
        testNormalNegation();
        testZeroNegation();
        testMaxValueNegation();
        testNegativeToPositive();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void testMinValueThrowsArithmeticException() {
        try {
            Math.negateExact(Long.MIN_VALUE);
            fail("Expected ArithmeticException for Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            check("long overflow".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    private static void testNormalNegation() {
        check(Math.negateExact(5L) == -5L, "negateExact(5L) should be -5L");
        check(Math.negateExact(-7L) == 7L, "negateExact(-7L) should be 7L");
    }

    private static void testZeroNegation() {
        check(Math.negateExact(0L) == 0L, "negateExact(0L) should be 0L");
    }

    private static void testMaxValueNegation() {
        check(Math.negateExact(Long.MAX_VALUE) == -Long.MAX_VALUE,
              "negateExact(Long.MAX_VALUE) should be -Long.MAX_VALUE");
    }

    private static void testNegativeToPositive() {
        check(Math.negateExact(Long.MIN_VALUE + 1) == Long.MAX_VALUE,
              "negateExact(Long.MIN_VALUE + 1) should be Long.MAX_VALUE");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    private static void fail(String message) {
        failures++;
        System.err.println("FAIL: " + message);
    }
}
