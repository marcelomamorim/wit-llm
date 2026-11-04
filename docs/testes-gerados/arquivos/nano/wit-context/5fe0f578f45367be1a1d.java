/* @test    @summary Test for DayOfWeek.of(int) method to verify exception handling for invalid inputs    @run main DayOfWeekWitupTest */

import java.time.DayOfWeek;
import java.time.DateTimeException;

public class DayOfWeekWitupTest {

    public static void main(String[] args) {
        testValidValues();
        testInvalidValues();
    }

    private static void testValidValues() {
        for (int i = 1; i <= 7; i++) {
            DayOfWeek dow = DayOfWeek.of(i);
            assert dow != null : "DayOfWeek.of(" + i + ") should not be null";
            assert dow.getValue() == i : "Expected value " + i + " but got " + dow.getValue();
        }
    }

    private static void testInvalidValues() {
        int[] invalidValues = {0, 8, -1, 100};
        for (int invalid : invalidValues) {
            try {
                DayOfWeek.of(invalid);
                // If no exception thrown, fail the test
                throw new AssertionError("Expected DateTimeException for input: " + invalid);
            } catch (DateTimeException e) {
                // Expected exception, test passes
                assert e.getMessage().contains("Invalid value for DayOfWeek") : "Unexpected exception message: " + e.getMessage();
            }
        }
    }
}
