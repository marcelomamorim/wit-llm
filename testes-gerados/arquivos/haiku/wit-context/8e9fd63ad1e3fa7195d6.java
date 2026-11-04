/*
 * @test
 * @summary WITUP-derived checks for JapaneseDate.until(ChronoLocalDate): period values and null endDate rejection
 * @run main JapaneseDateWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.JapaneseDate;
import java.time.temporal.ChronoUnit;

public class JapaneseDateWitupTest {

    public static void main(String[] args) {
        JapaneseDate start = JapaneseDate.of(2001, 1, 1);

        ChronoPeriod p = start.until(LocalDate.of(2001, 3, 5));
        if (p.get(ChronoUnit.YEARS) != 0) {
            throw new RuntimeException("expected 0 years, got " + p.get(ChronoUnit.YEARS));
        }
        if (p.get(ChronoUnit.MONTHS) != 2) {
            throw new RuntimeException("expected 2 months, got " + p.get(ChronoUnit.MONTHS));
        }
        if (p.get(ChronoUnit.DAYS) != 4) {
            throw new RuntimeException("expected 4 days, got " + p.get(ChronoUnit.DAYS));
        }

        ChronoPeriod same = start.until(LocalDate.of(2001, 1, 1));
        if (same.get(ChronoUnit.YEARS) != 0 || same.get(ChronoUnit.MONTHS) != 0
                || same.get(ChronoUnit.DAYS) != 0) {
            throw new RuntimeException("expected zero period for identical dates");
        }

        boolean npe = false;
        try {
            start.until((ChronoLocalDate) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null endDate");
        }
    }
}
