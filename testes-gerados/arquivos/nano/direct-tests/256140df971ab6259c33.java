/* @test    @summary Test Adler32.update(byte[] b, int off, int len) method for correctness and exception handling    @run main Adler32WitupTest */

import java.util.zip.Adler32;

public class Adler32WitupTest {

    public static void main(String[] args) {
        testUpdateWithValidInput();
        testUpdateWithNullArray();
        testUpdateWithNegativeOffset();
        testUpdateWithNegativeLength();
        testUpdateWithOffsetExceedingArray();
    }

    static void testUpdateWithValidInput() {
        Adler32 adler = new Adler32();
        byte[] data = {1, 2, 3, 4, 5};
        adler.update(data, 1, 3); // should process bytes 2, 3, 4
        long checksum = adler.getValue();
        // Check that checksum is not zero and is a positive value
        if (checksum <= 0) {
            throw new AssertionError("Checksum should be positive, but was " + checksum);
        }
    }

    static void testUpdateWithNullArray() {
        Adler32 adler = new Adler32();
        try {
            adler.update(null, 0, 1);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testUpdateWithNegativeOffset() {
        Adler32 adler = new Adler32();
        byte[] data = {1, 2, 3};
        try {
            adler.update(data, -1, 2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testUpdateWithNegativeLength() {
        Adler32 adler = new Adler32();
        byte[] data = {1, 2, 3};
        try {
            adler.update(data, 0, -1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testUpdateWithOffsetExceedingArray() {
        Adler32 adler = new Adler32();
        byte[] data = {1, 2, 3};
        try {
            adler.update(data, 2, 2); // off + len = 4 > data.length
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
