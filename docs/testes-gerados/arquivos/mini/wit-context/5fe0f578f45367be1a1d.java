/*
 * @test
 * @summary Test DayOfWeek.of(int) method for valid and invalid inputs
 * @run main DayOfWeekWitupTest
 */

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
            if (dow == null) {
                throw new AssertionError("DayOfWeek.of(" + i + ") returned null");
            }
            int value = dow.getValue();
            if (value != i) {
                throw new AssertionError("DayOfWeek.of(" + i + ").getValue() returned " + value + ", expected " + i);
            }
        }
    }

    private static void testInvalidValues() {
        int[] invalidInputs = {0, -1, 8, 100};
        for (int invalid : invalidInputs) {
            try {
                DayOfWeek.of(invalid);
                throw new AssertionError("DayOfWeek.of(" + invalid + ") did not throw DateTimeException");
            } catch (DateTimeException e) {
                // Expected exception
            } catch (Exception e) {
                throw new AssertionError("DayOfWeek.of(" + invalid + ") threw wrong exception: " + e);
            }
        }
    }
}
