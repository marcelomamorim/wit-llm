/*
 * @test
 * @summary Tests for DayOfWeek.of(int)
 * @run main DayOfWeekWitupTest_P2c80b6781ac6
 */

import java.time.DayOfWeek;
import java.time.DateTimeException;

public class DayOfWeekWitupTest_P2c80b6781ac6 {

    public static void main(String[] args) {
        testValidValues();
        testInvalidValues();
    }

    static void testValidValues() {
        for (int i = 1; i <= 7; i++) {
            DayOfWeek dow = DayOfWeek.of(i);
            assert dow != null : "DayOfWeek.of(" + i + ") should not be null";
            int value = dow.getValue();
            assert value == i : "Expected value " + i + ", but got " + value;
        }
    }

    static void testInvalidValues() {
        int[] invalids = {0, -1, 8, 100};
        for (int invalid : invalids) {
            try {
                DayOfWeek.of(invalid);
                assert false : "DayOfWeek.of(" + invalid + ") should throw DateTimeException";
            } catch (DateTimeException e) {
                // Expected exception
            }
        }
    }
}
