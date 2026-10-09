/* @test
   @summary Tests the static method DayOfWeek.of(int).
   @run main DayOfWeekWitupTest_P9f611ab04b72
 */
import java.time.DayOfWeek;
import java.time.DateTimeException;

public class DayOfWeekWitupTest_P9f611ab04b72 {
    public static void main(String[] args) {
        testValidValues();
        testInvalidValues();
    }

    static void testValidValues() {
        for (int i = 1; i <= 7; i++) {
            DayOfWeek dow = DayOfWeek.of(i);
            assert dow != null : "DayOfWeek.of(" + i + ") should not be null";
            assert dow.getValue() == i : "Expected value " + i + " but got " + dow.getValue();
        }
    }

    static void testInvalidValues() {
        int[] invalidValues = {0, -1, 8, 100};
        for (int invalid : invalidValues) {
            try {
                DayOfWeek.of(invalid);
                throw new AssertionError("Expected DateTimeException for input " + invalid);
            } catch (DateTimeException e) {
                // expected
            }
        }
    }
}
