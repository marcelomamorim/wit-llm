/*
 * @test
 * @summary Test java.util.zip.CRC32.update(byte[], int, int) method
 * @run main CRC32WitupTest
 */

import java.util.zip.CRC32;

public class CRC32WitupTest {
    public static void main(String[] args) {
        testNullArrayThrowsNPE();
        testNegativeOffsetThrowsAIOOBE();
        testNegativeLengthThrowsAIOOBE();
        testOffsetLengthOutOfBoundsThrowsAIOOBE();
        testUpdateWithValidInput();
    }

    private static void testNullArrayThrowsNPE() {
        CRC32 crc = new CRC32();
        try {
            crc.update(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNegativeOffsetThrowsAIOOBE() {
        CRC32 crc = new CRC32();
        byte[] b = new byte[10];
        try {
            crc.update(b, -1, 1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for negative offset");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testNegativeLengthThrowsAIOOBE() {
        CRC32 crc = new CRC32();
        byte[] b = new byte[10];
        try {
            crc.update(b, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for negative length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testOffsetLengthOutOfBoundsThrowsAIOOBE() {
        CRC32 crc = new CRC32();
        byte[] b = new byte[10];
        try {
            crc.update(b, 5, 6); // 5 + 6 = 11 > b.length
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for offset+length out of bounds");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testUpdateWithValidInput() {
        CRC32 crc = new CRC32();
        byte[] b = new byte[] {1, 2, 3, 4, 5};
        crc.update(b, 0, b.length);
        long val1 = crc.getValue();
        crc.update(b, 1, 3);
        long val2 = crc.getValue();
        if (val1 == 0 || val2 == 0) {
            throw new RuntimeException("CRC32 value should not be zero after update");
        }
        if (val1 == val2) {
            throw new RuntimeException("CRC32 value should change after additional update");
        }
    }
}
