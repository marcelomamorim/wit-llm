/*
 * @test
 * @summary Witup-derived checks for LocalDateTime.plusHours observable behavior and DateTimeException at year range overflow
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void checkThrowsDateTime(LocalDateTime value, long hours, String message) {
        try {
            LocalDateTime result = value.plusHours(hours);
            failures++;
            System.err.println("FAILED (no exception): " + message + " result=" + result);
        } catch (DateTimeException expected) {
        }
    }

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2018, 9, 15, 10, 30, 45, 500);

        check(base.plusHours(0).equals(base), "plusHours(0) should return an equal value");
        check(base.plusHours(1).equals(LocalDateTime.of(2018, 9, 15, 11, 30, 45, 500)),
              "plusHours(1) same day");
        check(base.plusHours(24).equals(LocalDateTime.of(2018, 9, 16, 10, 30, 45, 500)),
              "plusHours(24) next day");
        check(base.plusHours(-10).equals(LocalDateTime.of(2018, 9, 15, 0, 30, 45, 500)),
              "plusHours(-10) same day");
        check(LocalDateTime.of(2018, 12, 31, 23, 0).plusHours(2)
                .equals(LocalDateTime.of(2019, 1, 1, 1, 0)),
              "plusHours crossing year boundary");

        checkThrowsDateTime(LocalDateTime.of(999999999, 12, 31, 23, 0), 2,
                            "plusHours beyond maximum year");
        checkThrowsDateTime(LocalDateTime.of(-999999999, 1, 1, 0, 0), -1,
                            "plusHours beyond minimum year");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
