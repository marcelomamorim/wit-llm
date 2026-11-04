/*
 * @test
 * @summary Verifies HijrahChronology.dateEpochDay returns HijrahDate values that round-trip to the requested epoch day
 * @run main HijrahChronologyWitupTest
 */

import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;

public class HijrahChronologyWitupTest {

    public static void main(String[] args) {
        HijrahChronology chrono = HijrahChronology.INSTANCE;
        long[] epochDays = {0L, 1L, 20000L, -20000L};
        for (long epochDay : epochDays) {
            HijrahDate date = chrono.dateEpochDay(epochDay);
            if (date == null) {
                throw new RuntimeException("dateEpochDay returned null for " + epochDay);
            }
            if (date.toEpochDay() != epochDay) {
                throw new RuntimeException("Round trip failed for " + epochDay
                        + ": got " + date.toEpochDay());
            }
            if (date.getChronology() != chrono) {
                throw new RuntimeException("Unexpected chronology for " + epochDay);
            }
        }

        HijrahDate d1 = chrono.dateEpochDay(12345L);
        HijrahDate d2 = chrono.dateEpochDay(12345L);
        if (!d1.equals(d2)) {
            throw new RuntimeException("Same epoch day produced different dates");
        }
        if (chrono.dateEpochDay(12346L).equals(d1)) {
            throw new RuntimeException("Consecutive epoch days produced equal dates");
        }
    }
}
