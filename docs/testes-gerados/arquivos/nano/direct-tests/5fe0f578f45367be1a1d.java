/* @test    @summary Test DayOfWeek.of(int) for valid and invalid inputs    @run main DayOfWeekWitupTest */

import java.time.DayOfWeek;
import java.time.DateTimeException;

public class DayOfWeekWitupTest {

    public static void main(String[] args) {
        testValidValues();
        testInvalidValues();
    }

    static void testValidValues() {
        for (int i = 1; i <= 7; i++) {
            DayOfWeek dow = DayOfWeek.of(i);
            assert dow != null : "DayOfWeek.of(" + i + ") returned null";
            assert dow.getValue() == i : "Expected value " + i + " but got " + dow.getValue();
        }
    }

    static void testInvalidValues() {
        int[] invalidValues = {0, -1, 8, 100};
        for (int invalid : invalidValues) {
            try {
                DayOfWeek.of(invalid);
                assert false : "Expected DateTimeException for input " + invalid;
            } catch (DateTimeException e) {
                // Expected exception
            }
        }
    }
}
