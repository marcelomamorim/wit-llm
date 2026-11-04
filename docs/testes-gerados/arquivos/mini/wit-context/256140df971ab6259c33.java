/*
 * @test
 * @summary Test java.util.zip.Adler32.update(byte[], int, int) for exception and normal behavior
 * @run main Adler32WitupTest
 */

import java.util.zip.Adler32;

public class Adler32WitupTest {
    public static void main(String[] args) {
        testNullArrayThrows();
        testInvalidOffsetsThrow();
        testValidUpdate();
    }

    private static void testNullArrayThrows() {
        Adler32 adler = new Adler32();
        try {
            adler.update(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testInvalidOffsetsThrow() {
        Adler32 adler = new Adler32();
        byte[] data = new byte[5];

        // off < 0
        try {
            adler.update(data, -1, 1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for off < 0 not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }

        // len < 0
        try {
            adler.update(data, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for len < 0 not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }

        // off + len > data.length
        try {
            adler.update(data, 3, 3);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for off+len > data.length not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testValidUpdate() {
        Adler32 adler = new Adler32();
        byte[] data = {1, 2, 3, 4, 5};
        adler.update(data, 1, 3); // updates with bytes 2,3,4
        long checksum1 = adler.getValue();

        // Update again with same bytes and check checksum changes
        adler.update(data, 1, 3);
        long checksum2 = adler.getValue();

        if (checksum1 == 0) {
            throw new RuntimeException("Checksum should not be zero after update");
        }
        if (checksum2 == checksum1) {
            throw new RuntimeException("Checksum should change after second update");
        }
    }
}
