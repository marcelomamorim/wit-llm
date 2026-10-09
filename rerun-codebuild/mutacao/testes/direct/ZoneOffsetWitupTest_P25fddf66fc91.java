/* @test    @summary Test of ZoneOffset.ofTotalSeconds(int) for valid and invalid inputs    @run main ZoneOffsetWitupTest_P25fddf66fc91 */

import java.time.ZoneOffset;
import java.time.DateTimeException;

public class ZoneOffsetWitupTest_P25fddf66fc91 {

    public static void main(String[] args) {
        testValidOffset();
        testInvalidOffsetTooSmall();
        testInvalidOffsetTooLarge();
        testOffsetMultipleOfQuarterHour();
        testOffsetNonMultipleOfQuarterHour();
    }

    static void testValidOffset() {
        int[] validOffsets = {0, 900, -900, 5400, -5400, 64800, -64800};
        for (int offset : validOffsets) {
            ZoneOffset z = ZoneOffset.ofTotalSeconds(offset);
            assert z.getTotalSeconds() == offset : "Expected offset: " + offset + ", but got: " + z.getTotalSeconds();
        }
    }

    static void testInvalidOffsetTooSmall() {
        int invalidOffset = -64801; // less than -18:00 in seconds
        try {
            ZoneOffset.ofTotalSeconds(invalidOffset);
            assert false : "Expected DateTimeException for offset: " + invalidOffset;
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidOffsetTooLarge() {
        int invalidOffset = 64801; // more than +18:00 in seconds
        try {
            ZoneOffset.ofTotalSeconds(invalidOffset);
            assert false : "Expected DateTimeException for offset: " + invalidOffset;
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testOffsetMultipleOfQuarterHour() {
        int[] multiples = {0, 900, -900, 5400, -5400};
        for (int offset : multiples) {
            ZoneOffset z = ZoneOffset.ofTotalSeconds(offset);
            assert z.getTotalSeconds() == offset : "Expected offset: " + offset + ", but got: " + z.getTotalSeconds();
        }
    }

    static void testOffsetNonMultipleOfQuarterHour() {
        int offset = 100; // not multiple of 900 seconds
        ZoneOffset z = ZoneOffset.ofTotalSeconds(offset);
        assert z.getTotalSeconds() == offset : "Expected offset: " + offset + ", but got: " + z.getTotalSeconds();
    }
}
