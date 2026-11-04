/*
 * @test
 * @summary Checks ValueRange.checkValidValue returns valid values and throws DateTimeException for out-of-range values
 * @run main ValueRangeWitupTest
 */

import java.time.DateTimeException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        ValueRange r = ValueRange.of(1, 10);
        check(r.checkValidValue(5, null) == 5, "middle value should be returned");
        check(r.checkValidValue(1, null) == 1, "minimum value should be returned");
        check(r.checkValidValue(10, null) == 10, "maximum value should be returned");
        expectInvalid(r, 0, null);
        expectInvalid(r, 11, null);
        expectInvalid(r, Long.MIN_VALUE, null);

        ValueRange dom = ChronoField.DAY_OF_MONTH.range();
        check(dom.checkValidValue(28, ChronoField.DAY_OF_MONTH) == 28, "valid day should be returned");
        expectInvalid(dom, 0, ChronoField.DAY_OF_MONTH);
        expectInvalid(dom, 32, ChronoField.DAY_OF_MONTH);

        ValueRange full = ValueRange.of(Long.MIN_VALUE, Long.MAX_VALUE);
        check(full.checkValidValue(Long.MIN_VALUE, null) == Long.MIN_VALUE, "Long.MIN_VALUE should be valid");
        check(full.checkValidValue(Long.MAX_VALUE, null) == Long.MAX_VALUE, "Long.MAX_VALUE should be valid");
    }

    static void expectInvalid(ValueRange range, long value, TemporalField field) {
        try {
            range.checkValidValue(value, field);
        } catch (DateTimeException e) {
            String msg = e.getMessage();
            check(msg != null && msg.contains("Invalid value"), "unexpected message: " + msg);
            return;
        }
        throw new RuntimeException("expected DateTimeException for value " + value);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
