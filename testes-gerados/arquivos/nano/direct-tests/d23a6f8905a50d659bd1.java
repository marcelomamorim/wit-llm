/* @test    @summary Test for StringUTF16.checkBoundsOffCount method behavior and exception handling    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testValidBounds();
        testNegativeOffset();
        testOffsetExceedsLength();
        testCountExceedsRemaining();
        testZeroOffsetAndCount();
    }

    static void testValidBounds() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(0, 10, data);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid bounds", e);
        }
    }

    static void testNegativeOffset() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(-1, 5, data);
            throw new AssertionError("Expected NegativeArraySizeException or IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException | NegativeArraySizeException e) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type for negative offset", t);
        }
    }

    static void testOffsetExceedsLength() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(11, 1, data);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset exceeding length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCountExceedsRemaining() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(5, 6, data);
            throw new AssertionError("Expected IndexOutOfBoundsException for count exceeding remaining length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testZeroOffsetAndCount() {
        byte[] data = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(0, 0, data);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero offset and count", e);
        }
    }
}
