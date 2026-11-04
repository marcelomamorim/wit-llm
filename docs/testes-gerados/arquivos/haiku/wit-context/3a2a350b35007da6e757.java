/*
 * @test
 * @summary Witup-generated tests for LocalTime.ofSecondOfDay boundary and exception behavior
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        check(LocalTime.ofSecondOfDay(0L), 0, 0, 0);
        check(LocalTime.ofSecondOfDay(3661L), 1, 1, 1);
        check(LocalTime.ofSecondOfDay(86399L), 23, 59, 59);
        expectDateTimeException(-1L);
        expectDateTimeException(86400L);
        expectDateTimeException(Long.MIN_VALUE);
        expectDateTimeException(Long.MAX_VALUE);
    }

    private static void check(LocalTime t, int h, int m, int s) {
        if (t.getHour() != h || t.getMinute() != m || t.getSecond() != s || t.getNano() != 0) {
            throw new RuntimeException("unexpected " + t);
        }
    }

    private static void expectDateTimeException(long v) {
        try {
            LocalTime.ofSecondOfDay(v);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("no DateTimeException for " + v);
    }
}
