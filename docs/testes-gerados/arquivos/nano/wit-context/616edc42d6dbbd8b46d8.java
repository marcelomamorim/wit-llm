/* @test    @summary Test for UnicodeSet constructor with valid and invalid ranges    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRangeBelow();
        testInvalidRangeAbove();
        testInvalidRangeBoth();
    }

    private static void testValidRange() {
        try {
            UnicodeSet uset = new UnicodeSet(0, 10);
            if (uset == null) {
                throw new AssertionError("Expected non-null UnicodeSet for valid range");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid range: " + e);
        }
    }

    private static void testInvalidRangeBelow() {
        try {
            UnicodeSet uset = new UnicodeSet(-1, 5);
            throw new AssertionError("Expected IllegalArgumentException for start below LOW");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testInvalidRangeAbove() {
        try {
            UnicodeSet uset = new UnicodeSet(0, 0x110000);
            throw new AssertionError("Expected IllegalArgumentException for end above HIGH");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testInvalidRangeBoth() {
        try {
            UnicodeSet uset = new UnicodeSet(-5, 0x110000);
            throw new AssertionError("Expected IllegalArgumentException for start below LOW and end above HIGH");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
