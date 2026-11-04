/*
 * @test
 * @summary Verifies the public ZoneInfo(String ID, int rawOffset) constructor exposes the given ID and raw offset
 * @modules java.base/sun.util.calendar
 * @run main ZoneInfoWitupTest
 */

import sun.util.calendar.ZoneInfo;

public class ZoneInfoWitupTest {

    public static void main(String[] args) {
        ZoneInfo positive = new ZoneInfo("Test/Fixed", 3600000);
        check("Test/Fixed".equals(positive.getID()), "ID mismatch: " + positive.getID());
        check(positive.getRawOffset() == 3600000, "raw offset mismatch: " + positive.getRawOffset());
        check(positive.getOffset(0L) == 3600000, "offset at epoch mismatch: " + positive.getOffset(0L));

        ZoneInfo negative = new ZoneInfo("Test/West", -18000000);
        check("Test/West".equals(negative.getID()), "ID mismatch: " + negative.getID());
        check(negative.getRawOffset() == -18000000, "negative raw offset mismatch: " + negative.getRawOffset());
        check(negative.getOffset(0L) == -18000000, "negative offset at epoch mismatch: " + negative.getOffset(0L));

        ZoneInfo zero = new ZoneInfo("Test/UTC", 0);
        check("Test/UTC".equals(zero.getID()), "ID mismatch: " + zero.getID());
        check(zero.getRawOffset() == 0, "zero raw offset mismatch: " + zero.getRawOffset());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
