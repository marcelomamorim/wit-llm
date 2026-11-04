/*
 * @test
 * @summary Behavioral tests for ZoneId.of(String) using public observable results and exceptions
 * @run main ZoneIdWitupTest
 */

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ZoneIdWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        testUtcOffsetLetterZ();
        testOffsetIdPreserved();
        testRegionIdPreserved();
        testNullThrowsNpe();
        testEmptyStringThrows();
        testUnknownRegionThrows();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testUtcOffsetLetterZ() {
        ZoneId z = ZoneId.of("Z");
        check(z.equals(ZoneOffset.UTC), "ZoneId.of(\"Z\") should equal ZoneOffset.UTC");
        check("Z".equals(z.getId()), "ZoneId.of(\"Z\").getId() should be \"Z\"");
    }

    static void testOffsetIdPreserved() {
        ZoneId z = ZoneId.of("+01:00");
        check("+01:00".equals(z.getId()), "offset id should be preserved, got " + z.getId());
        check(z.equals(ZoneOffset.of("+01:00")), "ZoneId.of(\"+01:00\") should equal ZoneOffset.of(\"+01:00\")");
    }

    static void testRegionIdPreserved() {
        ZoneId paris = ZoneId.of("Europe/Paris");
        check("Europe/Paris".equals(paris.getId()), "region id should be preserved, got " + paris.getId());

        ZoneId utc = ZoneId.of("UTC");
        check("UTC".equals(utc.getId()), "ZoneId.of(\"UTC\").getId() should be \"UTC\", got " + utc.getId());
    }

    static void testNullThrowsNpe() {
        boolean thrown = false;
        try {
            ZoneId.of(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "ZoneId.of(null) should throw NullPointerException");
    }

    static void testEmptyStringThrows() {
        boolean thrown = false;
        try {
            ZoneId.of("");
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "ZoneId.of(\"\") should throw DateTimeException");
    }

    static void testUnknownRegionThrows() {
        boolean thrown = false;
        try {
            ZoneId.of("Invalid/Zone_X");
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "ZoneId.of(\"Invalid/Zone_X\") should throw DateTimeException");
    }
}
