/*
 * @test
 * @summary Tests LocalTime.ofNanoOfDay for valid boundary and sample values and for out-of-range rejection
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    static final long NANOS_PER_HOUR = 3600L * 1000_000_000L;
    static final long NANOS_PER_DAY = 24L * NANOS_PER_HOUR;

    public static void main(String[] args) throws Exception {
        testMidnight();
        testSampleDecomposition();
        testLastNanoOfDay();
        testEqualsOfFactory();
        testRoundTrip();
        testNegativeRejected();
        testTooLargeRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMidnight() {
        LocalTime t = LocalTime.ofNanoOfDay(0L);
        check(t.getHour() == 0, "hour of midnight");
        check(t.getMinute() == 0, "minute of midnight");
        check(t.getSecond() == 0, "second of midnight");
        check(t.getNano() == 0, "nano of midnight");
        check(t.equals(LocalTime.MIDNIGHT), "midnight equality");
    }

    static void testSampleDecomposition() {
        // 1h 2m 3s 4ns
        long nanos = 3723000000004L;
        LocalTime t = LocalTime.ofNanoOfDay(nanos);
        check(t.getHour() == 1, "hour decomposition");
        check(t.getMinute() == 2, "minute decomposition");
        check(t.getSecond() == 3, "second decomposition");
        check(t.getNano() == 4, "nano decomposition");
    }

    static void testLastNanoOfDay() {
        LocalTime t = LocalTime.ofNanoOfDay(NANOS_PER_DAY - 1);
        check(t.getHour() == 23, "last hour");
        check(t.getMinute() == 59, "last minute");
        check(t.getSecond() == 59, "last second");
        check(t.getNano() == 999999999, "last nano");
    }

    static void testEqualsOfFactory() {
        LocalTime fromNanos = LocalTime.ofNanoOfDay(3723000000004L);
        LocalTime fromFields = LocalTime.of(1, 2, 3, 4);
        check(fromNanos.equals(fromFields), "ofNanoOfDay must equal of(h,m,s,n)");
    }

    static void testRoundTrip() {
        long[] samples = {0L, 1L, 1000000000L, NANOS_PER_HOUR * 12 + 34567L, NANOS_PER_DAY - 1};
        for (long s : samples) {
            check(LocalTime.ofNanoOfDay(s).toNanoOfDay() == s, "round trip for " + s);
        }
    }

    static void testNegativeRejected() {
        boolean thrown = false;
        try {
            LocalTime.ofNanoOfDay(-1L);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "negative nanoOfDay must be rejected");
    }

    static void testTooLargeRejected() {
        boolean thrown = false;
        try {
            LocalTime.ofNanoOfDay(NANOS_PER_DAY);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "nanoOfDay equal to a full day must be rejected");
    }
}
