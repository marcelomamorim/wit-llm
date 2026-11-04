/*
 * @test
 * @summary Test java.time.DayOfWeek.of(int) method for valid and invalid inputs
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
            if (dow.getValue() != i) {
                throw new AssertionError("DayOfWeek.of(" + i + ") returned " + dow + " with value " + dow.getValue());
            }
        }
    }

    private static void testInvalidValues() {
        int[] invalids = {0, -1, 8, 100};
        for (int invalid : invalids) {
            try {
                DayOfWeek.of(invalid);
                throw new AssertionError("Expected DateTimeException for value: " + invalid);
            } catch (DateTimeException e) {
                // expected
            }
        }
    }
}
