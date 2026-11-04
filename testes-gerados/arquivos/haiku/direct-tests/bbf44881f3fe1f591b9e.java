/*
 * @test
 * @summary Tests LocalTime.withHour(int) for same-hour identity, field preservation, valid boundaries and invalid hour rejection
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        LocalTime t = LocalTime.of(10, 15, 30, 123456789);

        LocalTime changed = t.withHour(5);
        check(changed.getHour() == 5, "hour should be 5 but was " + changed.getHour());
        check(changed.getMinute() == 15, "minute should be preserved");
        check(changed.getSecond() == 30, "second should be preserved");
        check(changed.getNano() == 123456789, "nano should be preserved");
        check(t.getHour() == 10, "original LocalTime must be immutable");

        check(t.withHour(10) == t, "withHour with same hour should return this");

        check(LocalTime.MIDNIGHT.withHour(0) == LocalTime.MIDNIGHT, "same hour on MIDNIGHT should return same instance");

        check(t.withHour(0).getHour() == 0, "hour 0 should be accepted");
        check(t.withHour(23).getHour() == 23, "hour 23 should be accepted");

        expectInvalid(t, -1);
        expectInvalid(t, 24);
    }

    static void expectInvalid(LocalTime t, int hour) {
        try {
            t.withHour(hour);
        } catch (java.time.DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for hour " + hour);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
