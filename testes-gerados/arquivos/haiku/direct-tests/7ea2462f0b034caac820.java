/*
 * @test
 * @summary Tests observable behavior of LocalDateTime.plusHours
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        check(LocalDateTime.of(2020, 1, 1, 10, 0).plusHours(5),
              LocalDateTime.of(2020, 1, 1, 15, 0), "plusHours within same day");

        check(LocalDateTime.of(2020, 1, 1, 10, 0).plusHours(14),
              LocalDateTime.of(2020, 1, 2, 0, 0), "plusHours crossing midnight");

        check(LocalDateTime.of(2020, 1, 1, 0, 0).plusHours(-1),
              LocalDateTime.of(2019, 12, 31, 23, 0), "negative plusHours crossing year boundary");

        check(LocalDateTime.of(2020, 1, 1, 0, 0).plusHours(8784),
              LocalDateTime.of(2021, 1, 1, 0, 0), "plusHours over leap year (366 days)");

        check(LocalDateTime.of(2020, 1, 1, 0, 0).plusHours(25),
              LocalDateTime.of(2020, 1, 2, 1, 0), "plusHours larger than one day");

        LocalDateTime withNanos = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 123456789);
        check(withNanos.plusHours(1),
              LocalDateTime.of(2020, 1, 1, 1, 0, 0, 123456789), "nanosecond preserved");

        LocalDateTime base = LocalDateTime.of(2020, 3, 15, 12, 30, 45);
        check(base.plusHours(0), base, "plusHours(0) returns equal value");

        LocalDateTime original = LocalDateTime.of(2020, 6, 10, 8, 15);
        check(original.plusHours(1).plusHours(-1), original, "plusHours is reversible by negative amount");

        check(LocalDateTime.of(2020, 2, 28, 23, 0).plusHours(1),
              LocalDateTime.of(2020, 2, 29, 0, 0), "plusHours into leap day");

        check(LocalDateTime.of(2019, 2, 28, 23, 0).plusHours(1),
              LocalDateTime.of(2019, 3, 1, 0, 0), "plusHours into non-leap March");
    }

    static void check(LocalDateTime actual, LocalDateTime expected, String msg) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }
}
