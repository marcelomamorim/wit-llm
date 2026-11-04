/*
 * @test
 * @summary Test java.time.Year.of(int) factory method
 * @run main YearWitupTest
 */

public class YearWitupTest {
    public static void main(String[] args) {
        testValidYear();
        testInvalidYearBelowRange();
        testInvalidYearAboveRange();
    }

    private static void testValidYear() {
        // Test with a valid year
        java.time.Year y = java.time.Year.of(2000);
        if (y.getValue() != 2000) {
            throw new AssertionError("Year.of(2000) returned Year with value " + y.getValue());
        }
    }

    private static void testInvalidYearBelowRange() {
        try {
            // YEAR field valid range is from -999_999_999 to 999_999_999
            // Test with a value below the minimum
            java.time.Year.of(-1_000_000_000);
            throw new AssertionError("Expected DateTimeException for year below valid range");
        } catch (java.time.DateTimeException e) {
            // Expected
        }
    }

    private static void testInvalidYearAboveRange() {
        try {
            // Test with a value above the maximum
            java.time.Year.of(1_000_000_000);
            throw new AssertionError("Expected DateTimeException for year above valid range");
        } catch (java.time.DateTimeException e) {
            // Expected
        }
    }
}
