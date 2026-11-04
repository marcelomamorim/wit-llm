/*
 * @test
 * @summary Witup-derived tests for LocalTime.atDate(LocalDate)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        LocalTime t = LocalTime.of(10, 30, 15, 123);
        LocalDate d = LocalDate.of(2020, 1, 15);

        LocalDateTime dt = t.atDate(d);
        check(dt.equals(LocalDateTime.of(2020, 1, 15, 10, 30, 15, 123)), "atDate combines date and time");
        check(dt.toLocalDate().equals(d), "date part preserved");
        check(dt.toLocalTime().equals(t), "time part preserved");

        boolean thrown = false;
        try {
            t.atDate(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null date must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
