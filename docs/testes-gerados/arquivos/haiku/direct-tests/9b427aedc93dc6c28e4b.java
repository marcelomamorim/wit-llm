/*
 * @test
 * @summary Verifies ChronoField.checkValidValue returns valid values and rejects values outside the field range
 * @run main ChronoFieldWitupTest
 */

import java.time.DateTimeException;
import java.time.temporal.ChronoField;

public class ChronoFieldWitupTest {

    public static void main(String[] args) {
        testValidValuesAreReturned();
        testInvalidValuesAreRejected();
    }

    static void testValidValuesAreReturned() {
        expectEquals(1L, ChronoField.MONTH_OF_YEAR.checkValidValue(1L));
        expectEquals(6L, ChronoField.MONTH_OF_YEAR.checkValidValue(6L));
        expectEquals(12L, ChronoField.MONTH_OF_YEAR.checkValidValue(12L));

        expectEquals(0L, ChronoField.HOUR_OF_DAY.checkValidValue(0L));
        expectEquals(23L, ChronoField.HOUR_OF_DAY.checkValidValue(23L));

        expectEquals(1L, ChronoField.DAY_OF_WEEK.checkValidValue(1L));
        expectEquals(7L, ChronoField.DAY_OF_WEEK.checkValidValue(7L));
    }

    static void testInvalidValuesAreRejected() {
        expectInvalid(ChronoField.MONTH_OF_YEAR, 0L);
        expectInvalid(ChronoField.MONTH_OF_YEAR, 13L);
        expectInvalid(ChronoField.MONTH_OF_YEAR, -1L);

        expectInvalid(ChronoField.HOUR_OF_DAY, 24L);
        expectInvalid(ChronoField.HOUR_OF_DAY, -1L);

        expectInvalid(ChronoField.DAY_OF_WEEK, 0L);
        expectInvalid(ChronoField.DAY_OF_WEEK, 8L);
    }

    static void expectEquals(long expected, long actual) {
        if (expected != actual) {
            throw new RuntimeException("unexpected value returned by checkValidValue");
        }
    }

    static void expectInvalid(ChronoField field, long value) {
        try {
            field.checkValidValue(value);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for out-of-range value");
    }
}
