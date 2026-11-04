/*
 * @test
 * @summary Tests for ChronoField.checkValidIntValue(long)
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;

public class ChronoFieldWitupTest {

    public static void main(String[] args) {
        testCheckValidIntValueWithinRange();
        testCheckValidIntValueBelowRange();
        testCheckValidIntValueAboveRange();
    }

    private static void testCheckValidIntValueWithinRange() {
        long validValue = 100L;
        try {
            int result = ChronoField.checkValidIntValue(validValue);
            System.out.println("Within range test passed, result: " + result);
        } catch (Exception e) {
            System.err.println("Within range test failed: " + e);
        }
    }

    private static void testCheckValidIntValueBelowRange() {
        long invalidValue = Long.MIN_VALUE;
        try {
            ChronoField.checkValidIntValue(invalidValue);
            System.err.println("Below range test failed: exception expected but not thrown.");
        } catch (java.time.DateTimeException e) {
            System.out.println("Below range test passed, caught expected exception.");
        } catch (Exception e) {
            System.err.println("Below range test failed: unexpected exception: " + e);
        }
    }

    private static void testCheckValidIntValueAboveRange() {
        long invalidValue = Long.MAX_VALUE;
        try {
            ChronoField.checkValidIntValue(invalidValue);
            System.err.println("Above range test failed: exception expected but not thrown.");
        } catch (java.time.DateTimeException e) {
            System.out.println("Above range test passed, caught expected exception.");
        } catch (Exception e) {
            System.err.println("Above range test failed: unexpected exception: " + e);
        }
    }
}
