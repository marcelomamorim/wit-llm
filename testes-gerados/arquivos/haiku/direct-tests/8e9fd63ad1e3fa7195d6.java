/*
 * @test
 * @summary JapaneseDate.until(ChronoLocalDate) returns a Japanese-chronology period with the expected years, months and days
 * @run main JapaneseDateWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.temporal.ChronoUnit;

public class JapaneseDateWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkPeriod(ChronoPeriod p, long years, long months, long days, String label) {
        check(p.get(ChronoUnit.YEARS) == years, label + " years expected " + years + " but was " + p.get(ChronoUnit.YEARS));
        check(p.get(ChronoUnit.MONTHS) == months, label + " months expected " + months + " but was " + p.get(ChronoUnit.MONTHS));
        check(p.get(ChronoUnit.DAYS) == days, label + " days expected " + days + " but was " + p.get(ChronoUnit.DAYS));
    }

    public static void main(String[] args) {
        JapaneseDate start = JapaneseDate.of(2018, 1, 1);

        // Same date gives a zero period
        ChronoPeriod zero = start.until(JapaneseDate.of(2018, 1, 1));
        checkPeriod(zero, 0, 0, 0, "same date");
        check(zero.isZero(), "same date period should be zero");

        // Months and days within the same year
        ChronoPeriod p1 = start.until(JapaneseDate.of(2018, 3, 15));
        checkPeriod(p1, 0, 2, 14, "2018-01-01 to 2018-03-15");

        // Years, months and days across year boundaries
        ChronoPeriod p2 = JapaneseDate.of(2000, 2, 10).until(JapaneseDate.of(2003, 5, 1));
        checkPeriod(p2, 3, 2, 21, "2000-02-10 to 2003-05-01");

        // Backward direction gives negated components
        ChronoPeriod p3 = JapaneseDate.of(2018, 3, 15).until(JapaneseDate.of(2018, 1, 1));
        checkPeriod(p3, 0, -2, -14, "2018-03-15 to 2018-01-01");
        check(p3.isNegative(), "backward period should be negative");

        // Result belongs to the Japanese chronology
        check(p1.getChronology() == JapaneseChronology.INSTANCE,
              "until should return a period in the Japanese chronology");

        // Consistency with the ISO computation through a LocalDate-based date
        JapaneseDate fromIso = JapaneseDate.from(LocalDate.of(2018, 1, 1));
        checkPeriod(fromIso.until(JapaneseDate.of(2018, 3, 15)), 0, 2, 14, "from LocalDate start");
    }
}
