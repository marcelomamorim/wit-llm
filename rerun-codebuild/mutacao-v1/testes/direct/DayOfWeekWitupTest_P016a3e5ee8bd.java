/* @test    @summary Test DayOfWeek.of(int) method for valid and invalid inputs    @run main DayOfWeekWitupTest_P016a3e5ee8bd */

import java.time.DayOfWeek;
import java.time.DateTimeException;

public class DayOfWeekWitupTest_P016a3e5ee8bd {
    public static void main(String[] args) {
        testValidValues();
        testInvalidValues();
    }

    static void testValidValues() {
        for (int i = 1; i <= 7; i++) {
            DayOfWeek dow = DayOfWeek.of(i);
            if (dow == null) {
                throw new AssertionError("DayOfWeek.of(" + i + ") returned null");
            }
            if (dow.getValue() != i) {
                throw new AssertionError("Expected value " + i + " but got " + dow.getValue());
            }
        }
    }

    static void testInvalidValues() {
        int[] invalids = {0, -1, 8, 100};
        for (int invalid : invalids) {
            try {
                DayOfWeek.of(invalid);
                throw new AssertionError("Expected exception for input: " + invalid);
            } catch (DateTimeException e) {
                // Expected exception
            }
        }
    }
}
