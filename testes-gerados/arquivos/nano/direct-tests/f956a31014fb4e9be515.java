/* @test    @summary Tests for Month.of(int) method to ensure correct behavior and exception handling.    @run main MonthWitupTest */

import java.time.Month;
import java.time.DateTimeException;

public class MonthWitupTest {

    public static void main(String[] args) {
        testValidMonths();
        testInvalidMonths();
    }

    static void testValidMonths() {
        for (int i = 1; i <= 12; i++) {
            Month m = Month.of(i);
            assert m != null : "Month.of(" + i + ") returned null";
            assert m.getValue() == i : "Expected value " + i + " but got " + m.getValue();
        }
    }

    static void testInvalidMonths() {
        int[] invalidValues = {0, -1, 13, 100};
        for (int invalid : invalidValues) {
            try {
                Month.of(invalid);
                assert false : "Expected DateTimeException for input " + invalid;
            } catch (DateTimeException e) {
                assert e.getMessage().contains("Invalid value for MonthOfYear") : "Unexpected exception message: " + e.getMessage();
            }
        }
    }
}
