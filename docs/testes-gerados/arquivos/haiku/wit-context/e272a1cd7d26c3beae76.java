/*
 * @test
 * @summary Checks ValueRange.checkValidIntValue for valid int values, range boundaries and out-of-range values
 * @run main ValueRangeWitupTest
 */
import java.time.DateTimeException;
import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {
    public static void main(String[] args) {
        ValueRange r = ValueRange.of(1, 10);
        check(r.checkValidIntValue(1, null) == 1, "min boundary");
        check(r.checkValidIntValue(5, null) == 5, "middle value");
        check(r.checkValidIntValue(10, null) == 10, "max boundary");
        expectInvalid(r, 0);
        expectInvalid(r, 11);
        expectInvalid(r, Long.MAX_VALUE);
        expectInvalid(r, Long.MIN_VALUE);

        ValueRange negative = ValueRange.of(-5, 5);
        check(negative.checkValidIntValue(-5, null) == -5, "negative min");
        check(negative.checkValidIntValue(0, null) == 0, "zero in negative range");
        check(negative.checkValidIntValue(5, null) == 5, "positive max");
        expectInvalid(negative, -6);
        expectInvalid(negative, 6);

        ValueRange intRange = ValueRange.of(Integer.MIN_VALUE, Integer.MAX_VALUE);
        check(intRange.checkValidIntValue(Integer.MIN_VALUE, null) == Integer.MIN_VALUE, "int min");
        check(intRange.checkValidIntValue(Integer.MAX_VALUE, null) == Integer.MAX_VALUE, "int max");
        expectInvalid(intRange, (long) Integer.MAX_VALUE + 1L);
        expectInvalid(intRange, (long) Integer.MIN_VALUE - 1L);

        ValueRange longRange = ValueRange.of(Long.MIN_VALUE, Long.MAX_VALUE);
        expectInvalid(longRange, (long) Integer.MAX_VALUE + 1L);
        expectInvalid(longRange, (long) Integer.MIN_VALUE - 1L);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectInvalid(ValueRange range, long value) {
        try {
            range.checkValidIntValue(value, null);
            throw new RuntimeException("no exception for value " + value);
        } catch (DateTimeException expected) {
        }
    }
}
