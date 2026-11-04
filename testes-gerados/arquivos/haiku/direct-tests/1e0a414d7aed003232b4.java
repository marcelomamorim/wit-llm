/*
 * @test
 * @summary Verifies LocalTime.withMinute returns this for an unchanged minute, updates only the minute for valid values, and rejects out-of-range minutes.
 * @run main LocalTimeWitupTest
 */

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        LocalTime t = LocalTime.of(10, 15, 20, 123456789);

        LocalTime same = t.withMinute(15);
        if (same != t) {
            throw new RuntimeException("withMinute with the same minute should return this");
        }

        LocalTime changed = t.withMinute(30);
        if (changed == t) {
            throw new RuntimeException("withMinute with a different minute should return a new instance");
        }
        check(changed.getHour(), 10, "hour");
        check(changed.getMinute(), 30, "minute");
        check(changed.getSecond(), 20, "second");
        check(changed.getNano(), 123456789, "nano");
        check(t.getMinute(), 15, "original minute must be unchanged");

        LocalTime zero = t.withMinute(0);
        check(zero.getMinute(), 0, "minute zero");
        check(zero.getHour(), 10, "hour after minute zero");

        LocalTime max = t.withMinute(59);
        check(max.getMinute(), 59, "minute 59");

        expectDateTimeException(60);
        expectDateTimeException(-1);
    }

    private static void expectDateTimeException(int minute) {
        try {
            LocalTime.of(10, 15).withMinute(minute);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for minute " + minute);
    }

    private static void check(int actual, int expected, String what) {
        if (actual != expected) {
            throw new RuntimeException(what + ": expected " + expected + " but was " + actual);
        }
    }
}
