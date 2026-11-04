/* @test    @summary Test for Month.of(int) method to verify exception handling and valid inputs    @run main MonthWitupTest */

import static java.time.Month.*;

public class MonthWitupTest {

    public static void main(String[] args) {
        testValidMonths();
        testInvalidMonths();
    }

    private static void testValidMonths() {
        for (int m = 1; m <= 12; m++) {
            Month month = Month.of(m);
            assert month != null : "Month.of(" + m + ") returned null";
        }
    }

    private static void testInvalidMonths() {
        int[] invalidMonths = {0, 13, -1, 100};
        for (int invalid : invalidMonths) {
            try {
                Month.of(invalid);
                assert false : "Expected DateTimeException for input: " + invalid;
            } catch (java.time.DateTimeException e) {
                // Expected exception, test passes
            }
        }
    }
}
