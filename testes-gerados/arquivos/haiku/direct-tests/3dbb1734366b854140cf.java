/*
 * @test
 * @summary Verifies LocalDateTime.plusNanos observable behavior: small deltas, day rollover, leap day, zero, large round trips and consistency with plusHours
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0);

        check(base.plusNanos(1).equals(LocalDateTime.of(2019, 1, 1, 0, 0, 0, 1)),
              "plusNanos(1) from midnight should give 00:00:00.000000001");

        check(base.plusNanos(-1).equals(LocalDateTime.of(2018, 12, 31, 23, 59, 59, 999_999_999)),
              "plusNanos(-1) from midnight should go to previous day 23:59:59.999999999");

        check(base.plusNanos(1_000_000_000L).equals(LocalDateTime.of(2019, 1, 1, 0, 0, 1)),
              "plusNanos(1e9) should add exactly one second");

        LocalDateTime endOfDay = LocalDateTime.of(2019, 1, 1, 23, 59, 59, 999_999_999);
        check(endOfDay.plusNanos(1).equals(LocalDateTime.of(2019, 1, 2, 0, 0)),
              "plusNanos(1) at end of day should roll over to next day midnight");

        LocalDateTime endOfLeapFeb = LocalDateTime.of(2000, 2, 28, 23, 59, 59, 999_999_999);
        check(endOfLeapFeb.plusNanos(1).equals(LocalDateTime.of(2000, 2, 29, 0, 0)),
              "plusNanos(1) at end of 2000-02-28 should reach 2000-02-29 midnight");

        check(base.plusNanos(0).equals(base),
              "plusNanos(0) should return an equal value");

        check(base.plusNanos(3_600L * 1_000_000_000L).equals(base.plusHours(1)),
              "plusNanos of one hour in nanos should equal plusHours(1)");

        check(base.plusNanos(Long.MAX_VALUE).minusNanos(Long.MAX_VALUE).equals(base),
              "plusNanos(Long.MAX_VALUE) followed by minusNanos(Long.MAX_VALUE) should restore original");

        check(base.plusNanos(-Long.MAX_VALUE).minusNanos(-Long.MAX_VALUE).equals(base),
              "plusNanos(-Long.MAX_VALUE) followed by minusNanos(-Long.MAX_VALUE) should restore original");

        LocalDateTime mid = LocalDateTime.of(2019, 6, 15, 12, 30, 45, 123_456_789);
        check(mid.plusNanos(1_000_000_000L * 60L).equals(mid.plusMinutes(1)),
              "plusNanos of one minute in nanos should equal plusMinutes(1)");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
