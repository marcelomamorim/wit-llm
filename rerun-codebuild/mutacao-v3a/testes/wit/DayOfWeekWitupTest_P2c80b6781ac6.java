/* @test    @summary Test the static method DayOfWeek.of(int) for valid and invalid inputs    @run main DayOfWeekWitupTest_P2c80b6781ac6 */

import java.time.DayOfWeek;
import java.time.DateTimeException;

public class DayOfWeekWitupTest_P2c80b6781ac6 {

    public static void main(String[] args) {
        testValidValues();
        testInvalidValues();
    }

    private static void testValidValues() {
        // Test all valid days 1 to 7
        for (int i = 1; i <= 7; i++) {
            DayOfWeek dow = DayOfWeek.of(i);
            assert dow != null : "DayOfWeek.of(" + i + ") should not be null";
            int value = dow.getValue();
            assert value == i : "Expected value " + i + " but got " + value;
        }
    }

    private static void testInvalidValues() {
        int[] invalidValues = {0, 8, -1, 100};
        for (int invalid : invalidValues) {
            try {
                DayOfWeek.of(invalid);
                assert false : "Expected DateTimeException for input " + invalid;
            } catch (DateTimeException e) {
                // Expected exception
                assert e.getMessage().contains("Invalid value for DayOfWeek") : "Unexpected exception message: " + e.getMessage();
            }
        }
    }
}
