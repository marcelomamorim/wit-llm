/*
 * @test
 * @summary Witup-derived checks for ZoneInfo(String ID, int rawOffset): valid construction and null ID NullPointerException
 * @modules java.base/sun.util.calendar
 * @run main ZoneInfoWitupTest
 */

import sun.util.calendar.ZoneInfo;

public class ZoneInfoWitupTest {

    public static void main(String[] args) {
        ZoneInfo zi = new ZoneInfo("GMT+01", 3600000);
        check("GMT+01".equals(zi.getID()), "ID mismatch: " + zi.getID());
        check(zi.getRawOffset() == 3600000, "rawOffset mismatch: " + zi.getRawOffset());

        ZoneInfo neg = new ZoneInfo("Test/West", -18000000);
        check("Test/West".equals(neg.getID()), "ID mismatch for negative offset: " + neg.getID());
        check(neg.getRawOffset() == -18000000, "negative rawOffset mismatch: " + neg.getRawOffset());

        ZoneInfo zero = new ZoneInfo("UTC", 0);
        check(zero.getRawOffset() == 0, "zero rawOffset mismatch: " + zero.getRawOffset());

        boolean thrown = false;
        try {
            new ZoneInfo(null, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null ID");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
