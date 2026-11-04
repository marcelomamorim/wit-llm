/*
 * @test
 * @summary Verifies LocalDateTime.plusSeconds across day, month, year and leap-day boundaries, preserving nanoseconds
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        check(LocalDateTime.of(2020, 1, 1, 0, 0, 0).plusSeconds(1),
              LocalDateTime.of(2020, 1, 1, 0, 0, 1));

        check(LocalDateTime.of(2020, 1, 1, 0, 0, 0).plusSeconds(-1),
              LocalDateTime.of(2019, 12, 31, 23, 59, 59));

        check(LocalDateTime.of(2020, 2, 28, 23, 59, 59).plusSeconds(1),
              LocalDateTime.of(2020, 2, 29, 0, 0, 0));

        check(LocalDateTime.of(2019, 1, 1, 0, 0, 0).plusSeconds(365L * 86400L),
              LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        check(LocalDateTime.of(2020, 1, 1, 10, 0, 0, 123456789).plusSeconds(60),
              LocalDateTime.of(2020, 1, 1, 10, 1, 0, 123456789));

        check(LocalDateTime.of(2020, 3, 15, 12, 30, 45, 500).plusSeconds(0),
              LocalDateTime.of(2020, 3, 15, 12, 30, 45, 500));

        check(LocalDateTime.of(2020, 1, 1, 12, 0, 0).plusSeconds(86400),
              LocalDateTime.of(2020, 1, 2, 12, 0, 0));

        check(LocalDateTime.of(2020, 1, 1, 0, 0, 0).plusSeconds(3600),
              LocalDateTime.of(2020, 1, 1, 1, 0, 0));
    }

    static void check(LocalDateTime actual, LocalDateTime expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
