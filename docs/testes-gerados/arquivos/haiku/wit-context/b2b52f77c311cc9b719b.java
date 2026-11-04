/*
 * @test
 * @summary Behavioral checks for LocalDate.getDayOfWeek() on known dates and across a long consecutive range
 * @run main LocalDateWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        check(LocalDate.of(1970, 1, 1).getDayOfWeek(), DayOfWeek.THURSDAY);
        check(LocalDate.of(1969, 12, 31).getDayOfWeek(), DayOfWeek.WEDNESDAY);
        check(LocalDate.of(2000, 1, 1).getDayOfWeek(), DayOfWeek.SATURDAY);
        check(LocalDate.of(2024, 1, 1).getDayOfWeek(), DayOfWeek.MONDAY);

        LocalDate date = LocalDate.of(1000, 1, 1);
        DayOfWeek expected = date.getDayOfWeek();
        for (int i = 0; i < 800000; i++) {
            date = date.plusDays(1);
            expected = expected.plus(1);
            check(date.getDayOfWeek(), expected);
        }
    }

    private static void check(DayOfWeek actual, DayOfWeek expected) {
        if (actual != expected) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
