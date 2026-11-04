/*
 * @test
 * @summary Witup-style behavioral tests for ZoneOffset.ofTotalSeconds
 * @run main ZoneOffsetWitupTest
 */

import java.time.DateTimeException;
import java.time.ZoneOffset;

public class ZoneOffsetWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBoundsAccepted() {
        ZoneOffset max = ZoneOffset.ofTotalSeconds(64800);
        check(max.getTotalSeconds() == 64800, "max offset total seconds");
        check(max.getId().equals("+18:00"), "max offset id was " + max.getId());

        ZoneOffset min = ZoneOffset.ofTotalSeconds(-64800);
        check(min.getTotalSeconds() == -64800, "min offset total seconds");
        check(min.getId().equals("-18:00"), "min offset id was " + min.getId());
    }

    static void testOutOfRangeThrows() {
        int[] invalid = {64801, -64801, Integer.MAX_VALUE, Integer.MIN_VALUE};
        for (int value : invalid) {
            boolean thrown = false;
            try {
                ZoneOffset.ofTotalSeconds(value);
            } catch (DateTimeException e) {
                thrown = true;
                check(e.getMessage() != null
                        && e.getMessage().contains("Zone offset not in valid range"),
                        "unexpected message: " + e.getMessage());
            }
            check(thrown, "expected DateTimeException for " + value);
        }
    }

    static void testZeroIsUtc() {
        ZoneOffset zero = ZoneOffset.ofTotalSeconds(0);
        check(zero.getTotalSeconds() == 0, "zero total seconds");
        check(zero.getId().equals("Z"), "zero id was " + zero.getId());
        check(zero.equals(ZoneOffset.UTC), "zero should equal UTC");
    }

    static void testQuarterHourCachedInstance() {
        ZoneOffset a = ZoneOffset.ofTotalSeconds(3600);
        ZoneOffset b = ZoneOffset.ofTotalSeconds(3600);
        check(a == b, "quarter-hour offsets should be cached");
        check(a.equals(ZoneOffset.ofHours(1)), "+01:00 should equal ofHours(1)");
        check(a.getId().equals("+01:00"), "id was " + a.getId());
    }

    static void testNonQuarterHourOffset() {
        ZoneOffset a = ZoneOffset.ofTotalSeconds(1);
        check(a.getTotalSeconds() == 1, "one second total seconds");
        check(a.getId().equals("+00:00:01"), "id was " + a.getId());
        ZoneOffset b = ZoneOffset.ofTotalSeconds(1);
        check(a.equals(b), "non-quarter-hour offsets with same value should be equal");
        check(!a.equals(ZoneOffset.UTC), "+00:00:01 should not equal UTC");
    }

    static void testNegativeQuarterHour() {
        ZoneOffset a = ZoneOffset.ofTotalSeconds(-900);
        check(a.getTotalSeconds() == -900, "-900 total seconds");
        check(a.getId().equals("-00:15"), "id was " + a.getId());
    }

    public static void main(String[] args) {
        testBoundsAccepted();
        testOutOfRangeThrows();
        testZeroIsUtc();
        testQuarterHourCachedInstance();
        testNonQuarterHourOffset();
        testNegativeQuarterHour();
    }
}
