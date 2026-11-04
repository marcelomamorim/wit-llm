/*
 * @test
 * @summary Verifies HijrahChronology.date(int,int,int) returns a HijrahDate with the requested fields and rejects an invalid month
 * @run main HijrahChronologyWitupTest
 */

import java.time.DateTimeException;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.temporal.ChronoField;

public class HijrahChronologyWitupTest {

    public static void main(String[] args) {
        HijrahChronology hc = (HijrahChronology) Chronology.of("Hijrah-umalqura");

        HijrahDate d = hc.date(1434, 9, 1);
        check(d != null, "date() returned null");
        check(d.get(ChronoField.YEAR) == 1434, "year mismatch: " + d.get(ChronoField.YEAR));
        check(d.get(ChronoField.MONTH_OF_YEAR) == 9, "month mismatch: " + d.get(ChronoField.MONTH_OF_YEAR));
        check(d.get(ChronoField.DAY_OF_MONTH) == 1, "day mismatch: " + d.get(ChronoField.DAY_OF_MONTH));
        check(d.getChronology() == hc, "chronology of result is not the source chronology");

        HijrahDate d2 = hc.date(1434, 9, 1);
        check(d.equals(d2), "same inputs should produce equal dates");

        boolean thrown = false;
        try {
            hc.date(1434, 13, 1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "month 13 should be rejected with DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
