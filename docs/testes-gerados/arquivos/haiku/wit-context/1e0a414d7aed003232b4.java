/*
 * @test
 * @summary Tests LocalTime.withMinute return contract and DateTimeException for invalid minutes
 * @run main LocalTimeWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        LocalTime t = LocalTime.of(10, 30, 15, 500);

        if (t.withMinute(30) != t) {
            throw new RuntimeException("withMinute with same minute must return this");
        }

        LocalTime r = t.withMinute(45);
        if (r.getHour() != 10 || r.getMinute() != 45 || r.getSecond() != 15 || r.getNano() != 500) {
            throw new RuntimeException("unexpected result of withMinute(45): " + r);
        }

        if (t.withMinute(0).getMinute() != 0) {
            throw new RuntimeException("lower boundary minute 0 not accepted");
        }
        if (t.withMinute(59).getMinute() != 59) {
            throw new RuntimeException("upper boundary minute 59 not accepted");
        }

        expectDateTimeException(t, 60);
        expectDateTimeException(t, -1);
        expectDateTimeException(t, Integer.MIN_VALUE);
        expectDateTimeException(t, Integer.MAX_VALUE);
    }

    static void expectDateTimeException(LocalTime t, int minute) {
        try {
            t.withMinute(minute);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for minute " + minute);
    }
}
