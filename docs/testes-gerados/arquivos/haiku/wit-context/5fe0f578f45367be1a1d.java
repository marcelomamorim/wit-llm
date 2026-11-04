/*
 * @test
 * @summary Verifies DayOfWeek.of(int) returns the correct constant for 1..7 and throws DateTimeException outside that range
 * @run main DayOfWeekWitupTest
 */

import java.time.DateTimeException;
import java.time.DayOfWeek;

public class DayOfWeekWitupTest {

    public static void main(String[] args) {
        DayOfWeek[] expected = {
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
        };
        for (int i = 1; i <= 7; i++) {
            DayOfWeek d = DayOfWeek.of(i);
            check(d == expected[i - 1], "wrong constant for " + i);
            check(d.getValue() == i, "getValue mismatch for " + i);
        }
        expectDateTimeException(0);
        expectDateTimeException(8);
        expectDateTimeException(-1);
        expectDateTimeException(Integer.MIN_VALUE);
        expectDateTimeException(Integer.MAX_VALUE);
    }

    static void expectDateTimeException(int value) {
        try {
            DayOfWeek.of(value);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for " + value);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
