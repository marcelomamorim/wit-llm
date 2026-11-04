/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet constructor UnicodeSet(int start, int end)
 * @run main UnicodeSetWitupTest
 */

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeLowStart();
        testInvalidRangeHighStart();
        testInvalidRangeLowEnd();
        testInvalidRangeHighEnd();
    }

    private static void testValidRange() {
        // Test a valid range within BMP
        sun.text.normalizer.UnicodeSet set = new sun.text.normalizer.UnicodeSet(0x0041, 0x005A); // 'A' to 'Z'
        if (!set.contains(0x0041) || !set.contains(0x005A)) {
            throw new RuntimeException("UnicodeSet does not contain expected range characters");
        }
        if (set.contains(0x0061)) { // 'a' should not be contained
            throw new RuntimeException("UnicodeSet contains character outside the range");
        }
    }

    private static void testInvalidRangeLowStart() {
        // According to WIT, if start < LOW (0) and bmpSet and stringSpan are null, expect IllegalArgumentException
        // The current code does not expose bmpSet or stringSpan, so we test start < 0
        try {
            new sun.text.normalizer.UnicodeSet(-1, 10);
            throw new RuntimeException("Expected IllegalArgumentException for start < 0");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (UnsupportedOperationException e) {
            // Not expected here
            throw new RuntimeException("Unexpected UnsupportedOperationException for start < 0");
        }
    }

    private static void testInvalidRangeHighStart() {
        // Test start > HIGH - 1 (0x10FFFF - 1 = 0x10FFFE)
        // Use start = 0x110000 (one above max Unicode code point)
        try {
            new sun.text.normalizer.UnicodeSet(0x110000, 0x110001);
            throw new RuntimeException("Expected IllegalArgumentException for start > MAX_VALUE");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (UnsupportedOperationException e) {
            throw new RuntimeException("Unexpected UnsupportedOperationException for start > MAX_VALUE");
        }
    }

    private static void testInvalidRangeLowEnd() {
        // Test end < LOW (0)
        try {
            new sun.text.normalizer.UnicodeSet(0, -1);
            throw new RuntimeException("Expected IllegalArgumentException for end < 0");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (UnsupportedOperationException e) {
            throw new RuntimeException("Unexpected UnsupportedOperationException for end < 0");
        }
    }

    private static void testInvalidRangeHighEnd() {
        // Test end > HIGH - 1 (0x10FFFF - 1 = 0x10FFFE)
        try {
            new sun.text.normalizer.UnicodeSet(0, 0x110000);
            throw new RuntimeException("Expected IllegalArgumentException for end > MAX_VALUE");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (UnsupportedOperationException e) {
            throw new RuntimeException("Unexpected UnsupportedOperationException for end > MAX_VALUE");
        }
    }
}
