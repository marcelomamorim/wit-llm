/*
 * @test
 * @summary Witup-derived checks for LocalDateTime.minusMinutes observable behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        check(LocalDateTime.of(2020, 1, 1, 10, 30).minusMinutes(0),
              LocalDateTime.of(2020, 1, 1, 10, 30));

        check(LocalDateTime.of(2020, 1, 1, 10, 30).minusMinutes(60),
              LocalDateTime.of(2020, 1, 1, 9, 30));

        check(LocalDateTime.of(2020, 1, 1, 0, 0).minusMinutes(1),
              LocalDateTime.of(2019, 12, 31, 23, 59));

        check(LocalDateTime.of(2020, 3, 1, 0, 0).minusMinutes(1),
              LocalDateTime.of(2020, 2, 29, 23, 59));

        check(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 5).minusMinutes(1),
              LocalDateTime.of(2019, 12, 31, 23, 59, 0, 5));

        check(LocalDateTime.of(2020, 1, 1, 0, 0).minusMinutes(-1),
              LocalDateTime.of(2020, 1, 1, 0, 1));

        try {
            LocalDateTime.of(2020, 1, 1, 0, 0).minusMinutes(Long.MAX_VALUE);
            throw new RuntimeException("expected DateTimeException for Long.MAX_VALUE minutes");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    static void check(LocalDateTime actual, LocalDateTime expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
