/*
 * @test
 * @summary Verifies ChronoField.checkValidValue returns valid values and throws DateTimeException for out-of-range values
 * @run main ChronoFieldWitupTest
 */

import java.time.DateTimeException;

public class ChronoFieldWitupTest {

    public static void main(String[] args) throws Exception {
        checkValid(ChronoField.DAY_OF_MONTH, 1);
        checkValid(ChronoField.DAY_OF_MONTH, 31);
        checkValid(ChronoField.MONTH_OF_YEAR, 1);
        checkValid(ChronoField.MONTH_OF_YEAR, 12);
        checkValid(ChronoField.DAY_OF_WEEK, 7);

        checkInvalid(ChronoField.DAY_OF_MONTH, 0);
        checkInvalid(ChronoField.DAY_OF_MONTH, 32);
        checkInvalid(ChronoField.MONTH_OF_YEAR, 0);
        checkInvalid(ChronoField.MONTH_OF_YEAR, 13);
        checkInvalid(ChronoField.DAY_OF_WEEK, 8);
        checkInvalid(ChronoField.DAY_OF_MONTH, Long.MIN_VALUE);
        checkInvalid(ChronoField.DAY_OF_MONTH, Long.MAX_VALUE);
    }

    private static void checkValid(ChronoField field, long value) {
        long result = field.checkValidValue(value);
        if (result != value) {
            throw new RuntimeException("Expected " + value + " but got " + result + " for " + field);
        }
    }

    private static void checkInvalid(ChronoField field, long value) {
        try {
            field.checkValidValue(value);
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException for " + field + " value " + value);
    }
}
