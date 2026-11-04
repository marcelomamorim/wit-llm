/*
 * @test
 * @summary Test Integer.valueOf(String s, int radix) for valid and invalid inputs
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {
    public static void main(String[] args) {
        testValidRadixAndString();
        testNullStringThrowsNumberFormatException();
        testRadixTooLowThrowsNumberFormatException();
        testRadixTooHighThrowsNumberFormatException();
    }

    private static void testValidRadixAndString() {
        // Basic valid cases
        Integer i1 = Integer.valueOf("10", 10);
        if (i1.intValue() != 10) {
            throw new RuntimeException("Failed: Integer.valueOf(\"10\", 10) expected 10 but got " + i1);
        }
        Integer i2 = Integer.valueOf("a", 16);
        if (i2.intValue() != 10) {
            throw new RuntimeException("Failed: Integer.valueOf(\"a\", 16) expected 10 but got " + i2);
        }
        Integer i3 = Integer.valueOf("z", 36);
        if (i3.intValue() != 35) {
            throw new RuntimeException("Failed: Integer.valueOf(\"z\", 36) expected 35 but got " + i3);
        }
    }

    private static void testNullStringThrowsNumberFormatException() {
        try {
            Integer.valueOf(null, 10);
            throw new RuntimeException("Failed: Integer.valueOf(null, 10) did not throw NumberFormatException");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testRadixTooLowThrowsNumberFormatException() {
        try {
            Integer.valueOf("10", 1); // radix < 2
            throw new RuntimeException("Failed: Integer.valueOf(\"10\", 1) did not throw NumberFormatException");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testRadixTooHighThrowsNumberFormatException() {
        try {
            Integer.valueOf("10", 37); // radix > 36
            throw new RuntimeException("Failed: Integer.valueOf(\"10\", 37) did not throw NumberFormatException");
        } catch (NumberFormatException e) {
            // Expected
        }
    }
}
