/*
 * @test
 * @summary Verify DayOfWeek.of(int) maps values 1..7 to the matching constants and rejects out-of-range values
 * @run main DayOfWeekWitupTest
 */

import java.time.DateTimeException;
import java.time.DayOfWeek;

public class DayOfWeekWitupTest {

    public static void main(String[] args) {
        DayOfWeek[] all = DayOfWeek.values();
        for (int i = 1; i <= 7; i++) {
            DayOfWeek d = DayOfWeek.of(i);
            if (d.getValue() != i) {
                throw new RuntimeException("getValue mismatch for " + i + ": " + d.getValue());
            }
            if (d != all[i - 1]) {
                throw new RuntimeException("constant mismatch for " + i + ": " + d);
            }
        }
        if (DayOfWeek.of(1) != DayOfWeek.MONDAY) {
            throw new RuntimeException("of(1) should be MONDAY");
        }
        if (DayOfWeek.of(7) != DayOfWeek.SUNDAY) {
            throw new RuntimeException("of(7) should be SUNDAY");
        }

        expectDateTimeException(0);
        expectDateTimeException(-1);
        expectDateTimeException(8);
        expectDateTimeException(Integer.MIN_VALUE);
        expectDateTimeException(Integer.MAX_VALUE);
    }

    private static void expectDateTimeException(int value) {
        try {
            DayOfWeek.of(value);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for " + value);
    }
}
