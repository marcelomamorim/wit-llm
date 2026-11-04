/*
 * @test
 * @summary Basic behavior of OffsetTime.of(LocalTime, ZoneOffset)
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        LocalTime t = LocalTime.of(10, 15, 30, 123);
        ZoneOffset off = ZoneOffset.ofHours(1);

        OffsetTime ot = OffsetTime.of(t, off);

        check(ot.getHour() == 10, "hour");
        check(ot.getMinute() == 15, "minute");
        check(ot.getSecond() == 30, "second");
        check(ot.getNano() == 123, "nano");
        check(off.equals(ot.getOffset()), "offset");
        check(t.equals(ot.toLocalTime()), "toLocalTime");

        check(ot.equals(OffsetTime.of(10, 15, 30, 123, off)), "equals with int factory");
        check(!ot.equals(OffsetTime.of(t, ZoneOffset.UTC)), "different offset must not be equal");

        OffsetTime midnight = OffsetTime.of(LocalTime.MIDNIGHT, ZoneOffset.UTC);
        check(midnight.getHour() == 0 && midnight.getMinute() == 0, "midnight fields");
        check(ZoneOffset.UTC.equals(midnight.getOffset()), "UTC offset");

        OffsetTime max = OffsetTime.of(LocalTime.MAX, ZoneOffset.MIN);
        check(max.getHour() == 23 && max.getMinute() == 59 && max.getSecond() == 59, "max fields");
        check(ZoneOffset.MIN.equals(max.getOffset()), "min offset");

        OffsetTime simple = OffsetTime.of(LocalTime.of(10, 15), off);
        check("10:15+01:00".equals(simple.toString()), "toString: " + simple);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
