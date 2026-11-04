/*
 * @test
 * @summary LocalTime.withNano returns this for an unchanged nano, a new value otherwise, and rejects invalid nano values
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        LocalTime t = LocalTime.of(10, 20, 30, 400);

        if (t.withNano(400) != t) {
            throw new AssertionError("withNano with same nano must return this");
        }

        LocalTime u = t.withNano(500);
        if (u.getNano() != 500) {
            throw new AssertionError("nano not updated: " + u.getNano());
        }
        if (u.getHour() != 10 || u.getMinute() != 20 || u.getSecond() != 30) {
            throw new AssertionError("hour/minute/second changed: " + u);
        }
        if (!u.equals(LocalTime.of(10, 20, 30, 500))) {
            throw new AssertionError("unexpected result: " + u);
        }
        if (t.getNano() != 400) {
            throw new AssertionError("original value mutated");
        }

        LocalTime max = LocalTime.MIDNIGHT.withNano(999999999);
        if (max.getNano() != 999999999 || max.getHour() != 0) {
            throw new AssertionError("max nano failed: " + max);
        }

        LocalTime zero = LocalTime.of(23, 59, 59, 123).withNano(0);
        if (!zero.equals(LocalTime.of(23, 59, 59))) {
            throw new AssertionError("nano zero failed: " + zero);
        }

        expectDateTimeException(-1);
        expectDateTimeException(1000000000);
    }

    static void expectDateTimeException(int nano) {
        try {
            LocalTime.NOON.withNano(nano);
        } catch (DateTimeException e) {
            return;
        }
        throw new AssertionError("expected DateTimeException for nano " + nano);
    }
}
