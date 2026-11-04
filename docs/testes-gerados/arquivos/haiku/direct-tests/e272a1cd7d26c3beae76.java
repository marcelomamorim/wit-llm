/*
 * @test
 * @summary Verifies ValueRange.checkValidIntValue returns the int value for in-range inputs and throws DateTimeException otherwise
 * @run main ValueRangeWitupTest
 */

import java.time.DateTimeException;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        ValueRange dayRange = ValueRange.of(1, 31);
        check(dayRange.checkValidIntValue(1, ChronoField.DAY_OF_MONTH) == 1, "min boundary");
        check(dayRange.checkValidIntValue(15, ChronoField.DAY_OF_MONTH) == 15, "middle value");
        check(dayRange.checkValidIntValue(31, ChronoField.DAY_OF_MONTH) == 31, "max boundary");

        expectInvalid(dayRange, 0, ChronoField.DAY_OF_MONTH);
        expectInvalid(dayRange, 32, ChronoField.DAY_OF_MONTH);
        expectInvalid(dayRange, -1, ChronoField.DAY_OF_MONTH);

        ValueRange fullInt = ValueRange.of(Integer.MIN_VALUE, Integer.MAX_VALUE);
        check(fullInt.checkValidIntValue(Integer.MIN_VALUE, ChronoField.YEAR) == Integer.MIN_VALUE, "int min");
        check(fullInt.checkValidIntValue(Integer.MAX_VALUE, ChronoField.YEAR) == Integer.MAX_VALUE, "int max");

        ValueRange wide = ValueRange.of(1, Long.MAX_VALUE);
        expectInvalid(wide, 5, ChronoField.DAY_OF_MONTH);
        expectInvalid(wide, Integer.MAX_VALUE + 1L, ChronoField.DAY_OF_MONTH);
    }

    private static void expectInvalid(ValueRange range, long value, ChronoField field) {
        try {
            range.checkValidIntValue(value, field);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException for value " + value + " in " + range);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
