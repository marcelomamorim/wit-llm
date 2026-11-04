/*
 * @test
 * @summary Test java.time.Month.of(int) method for valid and invalid inputs
 * @run main MonthWitupTest
 */

import java.time.Month;
import java.time.DateTimeException;

public class MonthWitupTest {
    public static void main(String[] args) {
        testValidMonths();
        testInvalidMonths();
    }

    private static void testValidMonths() {
        for (int i = 1; i <= 12; i++) {
            Month m = Month.of(i);
            if (m.getValue() != i) {
                throw new AssertionError("Month.of(" + i + ") returned Month with value " + m.getValue());
            }
        }
    }

    private static void testInvalidMonths() {
        int[] invalidValues = {0, -1, 13, 100};
        for (int invalid : invalidValues) {
            boolean thrown = false;
            try {
                Month.of(invalid);
            } catch (DateTimeException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Month.of(" + invalid + ") did not throw DateTimeException");
            }
        }
    }
}
