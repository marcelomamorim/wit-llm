/*
 * @test
 * @summary ChronoField.checkValidIntValue returns valid int values and rejects out-of-range or non-int ranges
 * @run main ChronoFieldWitupTest
 */

import java.time.DateTimeException;
import java.time.temporal.ChronoField;

public class ChronoFieldWitupTest {

    public static void main(String[] args) {
        if (ChronoField.DAY_OF_MONTH.checkValidIntValue(1L) != 1) {
            throw new RuntimeException("DAY_OF_MONTH lower bound not returned");
        }
        if (ChronoField.DAY_OF_MONTH.checkValidIntValue(15L) != 15) {
            throw new RuntimeException("DAY_OF_MONTH valid value not returned");
        }
        if (ChronoField.DAY_OF_MONTH.checkValidIntValue(31L) != 31) {
            throw new RuntimeException("DAY_OF_MONTH upper bound not returned");
        }
        if (ChronoField.YEAR.checkValidIntValue(2018L) != 2018) {
            throw new RuntimeException("YEAR valid value not returned");
        }

        expectDateTimeException(ChronoField.DAY_OF_MONTH, 0L);
        expectDateTimeException(ChronoField.DAY_OF_MONTH, 32L);
        expectDateTimeException(ChronoField.DAY_OF_MONTH, -1L);
        expectDateTimeException(ChronoField.YEAR, 1000000000L);
        expectDateTimeException(ChronoField.YEAR, Long.MAX_VALUE);
        expectDateTimeException(ChronoField.NANO_OF_DAY, 0L);
    }

    private static void expectDateTimeException(ChronoField field, long value) {
        try {
            field.checkValidIntValue(value);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException for " + field + " with value " + value);
    }
}
