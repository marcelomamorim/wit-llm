/*
 * @test
 * @summary Basic observable behavior of DateTimeFormatter.parse(CharSequence)
 * @run main DateTimeFormatterWitupTest
 */
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        TemporalAccessor t = f.parse("2012-06-30");
        check(t.get(ChronoField.YEAR) == 2012, "year");
        check(t.get(ChronoField.MONTH_OF_YEAR) == 6, "month");
        check(t.get(ChronoField.DAY_OF_MONTH) == 30, "day");

        boolean npe = false;
        try {
            f.parse((CharSequence) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null text must throw NullPointerException");

        checkParseFails(f, "2012-06-30x");
        checkParseFails(f, "not a date");
        checkParseFails(f, "");
    }

    private static void checkParseFails(DateTimeFormatter f, String text) {
        boolean failed = false;
        try {
            f.parse(text);
        } catch (DateTimeParseException e) {
            failed = true;
        }
        check(failed, "expected DateTimeParseException for: " + text);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
