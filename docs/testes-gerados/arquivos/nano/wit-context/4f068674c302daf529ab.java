/*
 * @test
 * @summary Tests for java.time.ZoneOffset.ofTotalSeconds(int)
 * @run main ZoneOffsetWitupTest
 */

import java.time.ZoneOffset;
import java.time.DateTimeException;

public class ZoneOffsetWitupTest {

    public static void main(String[] args) {
        testValidTotalSeconds();
        testBelowMinimumTotalSeconds();
        testAboveMaximumTotalSeconds();
        testValidOffsetMultiple();
        testInvalidOffsetNonMultiple();
    }

    static void testValidTotalSeconds() {
        try {
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(0);
            System.out.println("testValidTotalSeconds passed: " + offset.getId());
        } catch (Exception e) {
            System.err.println("testValidTotalSeconds failed: " + e);
        }
    }

    static void testBelowMinimumTotalSeconds() {
        try {
            ZoneOffset.ofTotalSeconds(-64800 - 1); // below -18:00
            System.err.println("testBelowMinimumTotalSeconds failed: exception not thrown");
        } catch (DateTimeException e) {
            System.out.println("testBelowMinimumTotalSeconds passed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("testBelowMinimumTotalSeconds failed: " + e);
        }
    }

    static void testAboveMaximumTotalSeconds() {
        try {
            ZoneOffset.ofTotalSeconds(64800 + 1); // above +18:00
            System.err.println("testAboveMaximumTotalSeconds failed: exception not thrown");
        } catch (DateTimeException e) {
            System.out.println("testAboveMaximumTotalSeconds passed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("testAboveMaximumTotalSeconds failed: " + e);
        }
    }

    static void testValidOffsetMultiple() {
        try {
            int seconds = 15 * 60; // 900 seconds, multiple of 15 minutes
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(seconds);
            System.out.println("testValidOffsetMultiple passed: " + offset.getId());
        } catch (Exception e) {
            System.err.println("testValidOffsetMultiple failed: " + e);
        }
    }

    static void testInvalidOffsetNonMultiple() {
        try {
            int seconds = 900 + 1; // not multiple of 15 minutes
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(seconds);
            if (offset.getId().equals("+00:15:01")) {
                System.err.println("testInvalidOffsetNonMultiple failed: offset created with invalid seconds");
            } else {
                System.out.println("testInvalidOffsetNonMultiple passed: offset created with seconds " + seconds);
            }
        } catch (Exception e) {
            System.out.println("testInvalidOffsetNonMultiple passed: exception thrown - " + e.getMessage());
        }
    }
}
