/*
 * @test
 * @summary Verifies LocalTime.atDate(LocalDate) combines the date and time observably
 * @run main LocalTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        LocalTime t = LocalTime.of(10, 15, 30, 123);
        LocalDate d = LocalDate.of(2020, 2, 29);

        LocalDateTime dt = t.atDate(d);
        check(dt.toLocalDate().equals(d), "date part must equal the argument");
        check(dt.toLocalTime().equals(t), "time part must equal the receiver");
        check(dt.getHour() == 10, "hour mismatch");
        check(dt.getMinute() == 15, "minute mismatch");
        check(dt.getSecond() == 30, "second mismatch");
        check(dt.getNano() == 123, "nano mismatch");
        check(dt.equals(LocalDateTime.of(d, t)), "must equal LocalDateTime.of(date, time)");

        LocalDateTime midnight = LocalTime.MIDNIGHT.atDate(LocalDate.of(1999, 12, 31));
        check(midnight.equals(LocalDateTime.of(1999, 12, 31, 0, 0)), "midnight combination incorrect");

        LocalDateTime noon = LocalTime.NOON.atDate(LocalDate.of(2000, 1, 1));
        check(noon.getHour() == 12 && noon.getMinute() == 0, "noon combination incorrect");
        check(noon.toLocalDate().equals(LocalDate.of(2000, 1, 1)), "noon date incorrect");

        LocalDateTime negYear = LocalTime.of(23, 59, 59, 999999999).atDate(LocalDate.of(-1, 1, 1));
        check(negYear.getYear() == -1, "negative year not preserved");
        check(negYear.toLocalTime().getNano() == 999999999, "max nano not preserved");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
