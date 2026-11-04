/*
 * @test
 * @summary Verify LocalDateTime.plusMonths clamps the day of month and preserves the time fields
 * @run main LocalDateTimeWitupTest
 */
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2020, 1, 31, 10, 15, 30, 123);
        check(LocalDateTime.of(2020, 2, 29, 10, 15, 30, 123), base.plusMonths(1), "clamp 31 Jan + 1 month");
        check(base, base.plusMonths(0), "plusMonths(0) must return equal value");
        check(LocalDateTime.of(2021, 2, 28, 10, 15, 30, 123),
              LocalDateTime.of(2020, 2, 29, 10, 15, 30, 123).plusMonths(12), "leap day + 12 months");
        check(LocalDateTime.of(2020, 1, 31, 12, 0),
              LocalDateTime.of(2020, 3, 31, 12, 0).plusMonths(-2), "negative months within year");
        check(LocalDateTime.of(2019, 2, 15, 0, 0),
              LocalDateTime.of(2020, 3, 15, 0, 0).plusMonths(-13), "negative months across years");
        check(LocalDateTime.of(2020, 2, 29, 23, 59, 59),
              LocalDateTime.of(2019, 11, 30, 23, 59, 59).plusMonths(3), "crossing year boundary");
        LocalDateTime original = LocalDateTime.of(2020, 5, 10, 8, 30);
        original.plusMonths(7);
        check(LocalDateTime.of(2020, 5, 10, 8, 30), original, "receiver must be unchanged");
    }

    static void check(LocalDateTime expected, LocalDateTime actual, String msg) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }
}
