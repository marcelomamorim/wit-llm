/*
 * @test
 * @summary Tests for java.time.ZoneOffset.ofTotalSeconds(int)
 * @run main ZoneOffsetWitupTest_P6c8dc0b70a8b
 */

import java.time.ZoneOffset;
import java.time.DateTimeException;

public class ZoneOffsetWitupTest_P6c8dc0b70a8b {

    public static void main(String[] args) {
        testValidOffset();
        testOffsetBelowMinimum();
        testOffsetAboveMaximum();
        testOffsetNotMultipleOfQuarterHour();
    }

    private static void testValidOffset() {
        try {
            int validSeconds = 3600;
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(validSeconds);
            System.out.println("Valid offset test passed: " + offset.getId());
        } catch (Exception e) {
            throw new AssertionError("Valid offset test failed", e);
        }
    }

    private static void testOffsetBelowMinimum() {
        try {
            int invalidSeconds = -64800; // -18:00 - 1 second
            ZoneOffset.ofTotalSeconds(invalidSeconds);
            throw new AssertionError("Expected DateTimeException for offset below minimum") ;
        } catch (DateTimeException e) {
            System.out.println("Offset below minimum correctly threw DateTimeException");
        }
    }

    private static void testOffsetAboveMaximum() {
        try {
            int invalidSeconds = 64800; // +18:00 + 1 second
            ZoneOffset.ofTotalSeconds(invalidSeconds);
            throw new AssertionError("Expected DateTimeException for offset above maximum") ;
        } catch (DateTimeException e) {
            System.out.println("Offset above maximum correctly threw DateTimeException");
        }
    }

    private static void testOffsetNotMultipleOfQuarterHour() {
        try {
            int invalidSeconds = 3700; // not multiple of 900 (15 minutes)
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(invalidSeconds);
            System.out.println("Offset not multiple of quarter hour test passed: " + offset.getId());
        } catch (Exception e) {
            throw new AssertionError("Offset not multiple of quarter hour test failed", e);
        }
    }
}
