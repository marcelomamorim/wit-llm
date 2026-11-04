/*
 * @test
 * @summary Witup-derived tests for Year.get(TemporalField)
 * @run main YearWitupTest
 */
import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class YearWitupTest {
    public static void main(String[] args) {
        check(Year.of(2000).get(ChronoField.YEAR) == 2000, "YEAR for 2000");
        check(Year.of(2000).get(ChronoField.YEAR_OF_ERA) == 2000, "YEAR_OF_ERA for 2000");
        check(Year.of(2000).get(ChronoField.ERA) == 1, "ERA for 2000");
        check(Year.of(1).get(ChronoField.ERA) == 1, "ERA for 1");
        check(Year.of(0).get(ChronoField.ERA) == 0, "ERA for 0");
        check(Year.of(0).get(ChronoField.YEAR_OF_ERA) == 1, "YEAR_OF_ERA for 0");
        check(Year.of(-1).get(ChronoField.YEAR_OF_ERA) == 2, "YEAR_OF_ERA for -1");
        check(Year.of(Year.MAX_VALUE).get(ChronoField.YEAR_OF_ERA) == 999_999_999, "YEAR_OF_ERA for MAX_VALUE");
        check(Year.of(Year.MIN_VALUE).get(ChronoField.YEAR_OF_ERA) == 1_000_000_000, "YEAR_OF_ERA for MIN_VALUE");
        check(Year.of(Year.MIN_VALUE).get(ChronoField.YEAR) == Year.MIN_VALUE, "YEAR for MIN_VALUE");

        expectUnsupported(Year.of(2000), ChronoField.HOUR_OF_DAY);
        expectUnsupported(Year.of(2000), ChronoField.MONTH_OF_YEAR);
    }

    static void expectUnsupported(Year y, ChronoField f) {
        try {
            y.get(f);
        } catch (UnsupportedTemporalTypeException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedTemporalTypeException for " + f);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
