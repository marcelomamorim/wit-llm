/*
 * @test
 * @summary Witup-generated checks for LocalDateTime.plusSeconds
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2018, Month.SEPTEMBER, 15, 23, 59, 30, 500);

        check(base.plusSeconds(0).equals(base), "zero seconds keeps value");
        check(base.plusSeconds(30).equals(LocalDateTime.of(2018, 9, 16, 0, 0, 0, 500)),
              "crossing midnight by seconds");
        check(base.plusSeconds(60).equals(LocalDateTime.of(2018, 9, 16, 0, 0, 30, 500)),
              "adding one minute in seconds");
        check(base.plusSeconds(86400).equals(base.plusDays(1)),
              "one day in seconds equals plusDays(1)");
        check(base.plusSeconds(-86400).equals(base.minusDays(1)),
              "negative day in seconds equals minusDays(1)");
        check(LocalDateTime.of(2018, 1, 1, 0, 0, 0).plusSeconds(-1)
                .equals(LocalDateTime.of(2017, 12, 31, 23, 59, 59)),
              "year boundary backwards");
        check(base.plusSeconds(1).getNano() == 500,
              "nanosecond field preserved");

        expectDateTimeException(Long.MAX_VALUE);
        expectDateTimeException(Long.MIN_VALUE);
    }

    static void expectDateTimeException(long seconds) {
        LocalDateTime base = LocalDateTime.of(2018, Month.SEPTEMBER, 15, 12, 0, 0, 0);
        try {
            LocalDateTime result = base.plusSeconds(seconds);
            throw new RuntimeException("Expected DateTimeException for seconds=" + seconds
                    + " but got " + result);
        } catch (DateTimeException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
