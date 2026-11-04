/*
 * @test
 * @summary Witup-derived tests for LocalDateTime.minusHours
 * @run main LocalDateTimeWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2018, 3, 15, 10, 30, 45, 500);

        check(base.minusHours(0).equals(base), "minusHours(0) must return an equal value");
        check(base.minusHours(1).equals(LocalDateTime.of(2018, 3, 15, 9, 30, 45, 500)),
                "minusHours(1) within the same day");
        check(base.minusHours(48).equals(LocalDateTime.of(2018, 3, 13, 10, 30, 45, 500)),
                "minusHours(48) must cross two days");
        check(base.minusHours(24).equals(LocalDateTime.of(2018, 3, 14, 10, 30, 45, 500)),
                "minusHours(24) must move exactly one day back");
        check(LocalDateTime.of(2018, 1, 1, 0, 0).minusHours(1)
                .equals(LocalDateTime.of(2017, 12, 31, 23, 0)),
                "minusHours across midnight and year boundary");
        check(base.minusHours(-3).equals(base.plusHours(3)),
                "negative hours must behave as plusHours");

        assertThrowsDateTime(() -> LocalDateTime.MIN.minusHours(1), "below minimum supported year");
        assertThrowsDateTime(() -> base.minusHours(Long.MAX_VALUE), "Long.MAX_VALUE hours out of range");
    }

    private static void assertThrowsDateTime(Runnable r, String msg) {
        try {
            r.run();
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException: " + msg);
    }

    private static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }
}
