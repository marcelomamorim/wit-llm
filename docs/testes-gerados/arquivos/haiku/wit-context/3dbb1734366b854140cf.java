/*
 * @test
 * @summary Witup-derived checks for LocalDateTime.plusNanos normal and overflow behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) throws Exception {
        LocalDateTime base = LocalDateTime.of(2020, 1, 1, 0, 0, 0);

        LocalDateTime same = base.plusNanos(0);
        check(same.equals(base), 1);

        LocalDateTime plusOneSecondPlusOneNano = base.plusNanos(1000000001L);
        check(plusOneSecondPlusOneNano.getYear() == 2020, 2);
        check(plusOneSecondPlusOneNano.getMonthValue() == 1, 3);
        check(plusOneSecondPlusOneNano.getDayOfMonth() == 1, 4);
        check(plusOneSecondPlusOneNano.getHour() == 0, 5);
        check(plusOneSecondPlusOneNano.getMinute() == 0, 6);
        check(plusOneSecondPlusOneNano.getSecond() == 1, 7);
        check(plusOneSecondPlusOneNano.getNano() == 1, 8);

        LocalDateTime previousDay = base.plusNanos(-1);
        check(previousDay.equals(LocalDateTime.of(2019, 12, 31, 23, 59, 59, 999999999)), 9);

        check(base.plusNanos(1).plusNanos(-1).equals(base), 10);

        LocalDateTime max = LocalDateTime.of(999999999, 12, 31, 23, 59, 59, 999999999);
        expectDateTimeException(max, 1L, 11);
        expectDateTimeException(max, Long.MAX_VALUE, 12);

        LocalDateTime min = LocalDateTime.of(-999999999, 1, 1, 0, 0, 0);
        expectDateTimeException(min, -1L, 13);
    }

    private static void expectDateTimeException(LocalDateTime t, long nanos, int id) {
        try {
            t.plusNanos(nanos);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException(String.valueOf(id));
    }

    private static void check(boolean condition, int id) {
        if (!condition) {
            throw new RuntimeException(String.valueOf(id));
        }
    }
}
