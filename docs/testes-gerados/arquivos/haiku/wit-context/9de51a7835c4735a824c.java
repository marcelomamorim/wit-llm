/*
 * @test
 * @summary Witup-derived checks for LocalDateTime.withSecond(int): valid updates keep date, time fields and nanos; invalid seconds throw DateTimeException.
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2007, 12, 3, 10, 15, 30, 123456789);

        LocalDateTime zero = base.withSecond(0);
        check(zero.equals(LocalDateTime.of(2007, 12, 3, 10, 15, 0, 123456789)), "withSecond(0)");
        check(zero.getSecond() == 0, "second is 0");
        check(zero.getNano() == 123456789, "nano preserved for withSecond(0)");

        LocalDateTime max = base.withSecond(59);
        check(max.equals(LocalDateTime.of(2007, 12, 3, 10, 15, 59, 123456789)), "withSecond(59)");

        LocalDateTime same = base.withSecond(30);
        check(same.equals(base), "withSecond with same value returns equal value");

        LocalDateTime midnight = LocalDateTime.of(2007, 12, 31, 0, 0, 0).withSecond(45);
        check(midnight.equals(LocalDateTime.of(2007, 12, 31, 0, 0, 45)), "date boundary preserved");

        expectDateTimeException(base, 60);
        expectDateTimeException(base, -1);
        expectDateTimeException(base, 100);
    }

    private static void expectDateTimeException(LocalDateTime dt, int second) {
        try {
            dt.withSecond(second);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException for second = " + second);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
