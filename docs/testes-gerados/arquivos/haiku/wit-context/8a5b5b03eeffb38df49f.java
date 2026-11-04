/*
 * @test
 * @summary Checks HijrahChronology.dateEpochDay for a valid epoch day and for out-of-range epoch days
 * @run main HijrahChronologyWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;

public class HijrahChronologyWitupTest {

    public static void main(String[] args) {
        HijrahChronology chrono = HijrahChronology.INSTANCE;

        long valid = LocalDate.of(2000, 1, 1).toEpochDay();
        HijrahDate date = chrono.dateEpochDay(valid);
        if (date.toEpochDay() != valid) {
            throw new RuntimeException("epoch day round trip failed: " + date.toEpochDay() + " != " + valid);
        }
        if (date.getChronology() != chrono) {
            throw new RuntimeException("returned date has a different chronology");
        }

        expectDateTimeException(chrono, Long.MAX_VALUE);
        expectDateTimeException(chrono, Long.MIN_VALUE);
    }

    static void expectDateTimeException(HijrahChronology chrono, long epochDay) {
        try {
            chrono.dateEpochDay(epochDay);
        } catch (DateTimeException ex) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for epochDay " + epochDay);
    }
}
