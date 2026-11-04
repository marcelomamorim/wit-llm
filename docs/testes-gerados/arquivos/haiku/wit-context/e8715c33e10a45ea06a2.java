/*
 * @test
 * @summary Tests LocalDateTime.withNano valid, unchanged and out-of-range behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2018, 9, 15, 10, 20, 30, 123);

        LocalDateTime changed = base.withNano(456);
        check(changed.getNano() == 456, "withNano must set the nano value");
        check(changed.getYear() == 2018, "year must be preserved");
        check(changed.getMonthValue() == 9, "month must be preserved");
        check(changed.getDayOfMonth() == 15, "day must be preserved");
        check(changed.getHour() == 10, "hour must be preserved");
        check(changed.getMinute() == 20, "minute must be preserved");
        check(changed.getSecond() == 30, "second must be preserved");

        check(base.getNano() == 123, "original value must not be mutated");

        check(base.withNano(123).equals(base), "same nano must yield equal value");
        check(base.withNano(0).getNano() == 0, "zero nano must be accepted");
        check(base.withNano(999_999_999).getNano() == 999_999_999, "max nano must be accepted");

        expectDateTimeException(base, -1);
        expectDateTimeException(base, 1_000_000_000);
    }

    private static void expectDateTimeException(LocalDateTime value, int nano) {
        try {
            value.withNano(nano);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for nano " + nano);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
