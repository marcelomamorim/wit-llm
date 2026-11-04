/*
 * @test
 * @summary Test java.util.zip.CRC32.update(byte[] b, int off, int len) for exception and normal behavior
 * @run main CRC32WitupTest
 */

import java.util.zip.CRC32;

public class CRC32WitupTest {
    public static void main(String[] args) {
        testNullArrayThrowsNPE();
        testInvalidOffsetsThrowAIOOBE();
        testValidUpdateChangesCRC();
    }

    private static void testNullArrayThrowsNPE() {
        CRC32 crc = new CRC32();
        try {
            crc.update(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testInvalidOffsetsThrowAIOOBE() {
        CRC32 crc = new CRC32();
        byte[] data = new byte[5];

        // off < 0
        try {
            crc.update(data, -1, 1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for off < 0");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }

        // len < 0
        try {
            crc.update(data, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for len < 0");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }

        // off + len > data.length
        try {
            crc.update(data, 3, 3);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for off + len > data.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testValidUpdateChangesCRC() {
        CRC32 crc = new CRC32();
        byte[] data = {1, 2, 3, 4, 5};
        long before = crc.getValue();
        crc.update(data, 1, 3); // update with bytes 2,3,4
        long after = crc.getValue();
        if (before == after) {
            throw new RuntimeException("CRC value did not change after valid update");
        }
    }
}
