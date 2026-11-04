/*
 * @test
 * @summary WITUP-derived checks for HijrahChronology.date(int, int, int)
 * @run main HijrahChronologyWitupTest
 */

import java.time.DateTimeException;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.temporal.ChronoField;

public class HijrahChronologyWitupTest {

    interface Action {
        void run();
    }

    public static void main(String[] args) {
        final HijrahChronology hc = HijrahChronology.INSTANCE;

        HijrahDate d = hc.date(1433, 9, 1);
        check(d != null, "date returned null");
        check(d.getChronology() == hc, "chronology mismatch");
        check(d.get(ChronoField.YEAR) == 1433, "year mismatch");
        check(d.get(ChronoField.MONTH_OF_YEAR) == 9, "month mismatch");
        check(d.get(ChronoField.DAY_OF_MONTH) == 1, "day mismatch");

        expectDateTimeException(() -> hc.date(1433, 13, 1));
        expectDateTimeException(() -> hc.date(1433, 0, 1));
        expectDateTimeException(() -> hc.date(1433, 1, 0));
    }

    static void expectDateTimeException(Action action) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
