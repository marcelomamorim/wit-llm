/*
 * @test
 * @summary Test of ZoneId.ofOffset(String, ZoneOffset)
 * @run main ZoneIdWitupTest_P73aea5adb312
 */

import java.time.ZoneId;
import java.time.ZoneOffset;

public class ZoneIdWitupTest_P73aea5adb312 {

    public static void main(String[] args) {
        testOfOffsetWithValidPrefixes();
        testOfOffsetWithInvalidPrefix();
        testOfOffsetWithZeroOffset();
        testOfOffsetWithNonZeroOffset();
    }

    static void testOfOffsetWithValidPrefixes() {
        try {
            ZoneOffset offsetZero = ZoneOffset.ofTotalSeconds(0);
            ZoneId zoneGMT = ZoneId.ofOffset("GMT", offsetZero);
            ZoneId zoneUTC = ZoneId.ofOffset("UTC", offsetZero);
            ZoneId zoneUT = ZoneId.ofOffset("UT", offsetZero);
            if (!zoneGMT.getId().equals("GMT")) throw new AssertionError("Expected GMT, got " + zoneGMT.getId());
            if (!zoneUTC.getId().equals("UTC")) throw new AssertionError("Expected UTC, got " + zoneUTC.getId());
            if (!zoneUT.getId().equals("UT")) throw new AssertionError("Expected UT, got " + zoneUT.getId());
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid prefixes test", e);
        }
    }

    static void testOfOffsetWithInvalidPrefix() {
        try {
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(3600);
            ZoneId.ofOffset("INVALID", offset);
            throw new AssertionError("Expected IllegalArgumentException for invalid prefix");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static void testOfOffsetWithZeroOffset() {
        try {
            ZoneOffset offsetZero = ZoneOffset.ofTotalSeconds(0);
            ZoneId zone = ZoneId.ofOffset("GMT", offsetZero);
            if (!zone.getId().equals("GMT")) throw new AssertionError("Expected GMT, got " + zone.getId());
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in zero offset test", e);
        }
    }

    static void testOfOffsetWithNonZeroOffset() {
        try {
            ZoneOffset offsetPlus2 = ZoneOffset.ofTotalSeconds(7200);
            ZoneId zone = ZoneId.ofOffset("UTC", offsetPlus2);
            if (!zone.getId().equals("UTC+02:00")) {
                throw new AssertionError("Expected UTC+02:00, got " + zone.getId());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in non-zero offset test", e);
        }
    }
}
