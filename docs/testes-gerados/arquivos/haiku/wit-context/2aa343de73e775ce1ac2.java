/*
 * @test
 * @summary Checks that ValueRange.checkValidValue returns valid values and throws DateTimeException for out-of-range values
 * @run main ValueRangeWitupTest
 */

import java.time.DateTimeException;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testValidValuesReturnedUnchanged();
        testBoundariesOfSimpleRange();
        testOutOfRangeValuesThrow();
        testVariableRangeUsesOuterBounds();
        testNullFieldStillThrowsDateTimeException();
    }

    static void testValidValuesReturnedUnchanged() {
        ValueRange range = ValueRange.of(1, 31);
        check(range.checkValidValue(1, ChronoField.DAY_OF_MONTH) == 1, "min value returned");
        check(range.checkValidValue(15, ChronoField.DAY_OF_MONTH) == 15, "mid value returned");
        check(range.checkValidValue(31, ChronoField.DAY_OF_MONTH) == 31, "max value returned");
    }

    static void testBoundariesOfSimpleRange() {
        ValueRange range = ValueRange.of(1, 31);
        expectDateTimeException(range, 0L, ChronoField.DAY_OF_MONTH, "below minimum");
        expectDateTimeException(range, 32L, ChronoField.DAY_OF_MONTH, "above maximum");
    }

    static void testOutOfRangeValuesThrow() {
        ValueRange range = ValueRange.of(-5, 5);
        expectDateTimeException(range, Long.MIN_VALUE, ChronoField.DAY_OF_MONTH, "Long.MIN_VALUE");
        expectDateTimeException(range, Long.MAX_VALUE, ChronoField.DAY_OF_MONTH, "Long.MAX_VALUE");
        check(range.checkValidValue(-5, ChronoField.DAY_OF_MONTH) == -5, "negative min returned");
    }

    static void testVariableRangeUsesOuterBounds() {
        ValueRange range = ValueRange.of(1, 5, 10, 20);
        check(range.checkValidValue(1, ChronoField.DAY_OF_MONTH) == 1, "outer min returned");
        check(range.checkValidValue(20, ChronoField.DAY_OF_MONTH) == 20, "outer max returned");
        expectDateTimeException(range, 0L, ChronoField.DAY_OF_MONTH, "below outer minimum");
        expectDateTimeException(range, 21L, ChronoField.DAY_OF_MONTH, "above outer maximum");
    }

    static void testNullFieldStillThrowsDateTimeException() {
        ValueRange range = ValueRange.of(1, 7);
        expectDateTimeException(range, 8L, null, "null field invalid value");
    }

    static void expectDateTimeException(ValueRange range, long value,
                                        java.time.temporal.TemporalField field, String label) {
        try {
            long result = range.checkValidValue(value, field);
            throw new RuntimeException("Expected DateTimeException for " + label
                    + " but got " + result);
        } catch (DateTimeException expected) {
            if (expected.getMessage() == null || !expected.getMessage().contains("Invalid value")) {
                throw new RuntimeException("Unexpected message for " + label + ": "
                        + expected.getMessage());
            }
        }
    }

    static void check(boolean condition, String label) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + label);
        }
    }
}
