/* @test    @summary Test for DayOfWeek.of(int) method to verify exception handling for invalid inputs    @run main DayOfWeekWitupTest_P016a3e5ee8bd */

import java.time.DayOfWeek;
import java.time.DateTimeException;

public class DayOfWeekWitupTest_P016a3e5ee8bd {

    public static void main(String[] args) {
        testValidValues();
        testInvalidValues();
    }

    private static void testValidValues() {
        for (int i = 1; i <= 7; i++) {
            DayOfWeek dow = DayOfWeek.of(i);
            if (dow == null) {
                throw new AssertionError("Expected non-null DayOfWeek for value: " + i);
            }
            if (dow.getValue() != i) {
                throw new AssertionError("Mismatch for value: " + i + ", got: " + dow.getValue());
            }
        }
    }

    private static void testInvalidValues() {
        int[] invalidValues = {0, 8, -1, 100};
        for (int invalid : invalidValues) {
            try {
                DayOfWeek.of(invalid);
                throw new AssertionError("Expected DateTimeException for invalid value: " + invalid);
            } catch (DateTimeException e) {
                // Expected exception, test passes
            } catch (Throwable t) {
                throw new AssertionError("Unexpected exception type for value: " + invalid, t);
            }
        }
    }
}
