/*
 * @test
 * @summary Test java.util.zip.CRC32C update(byte[], int, int) method
 * @run main CRC32CWitupTest
 */

import java.util.zip.CRC32C;

public class CRC32CWitupTest {
    public static void main(String[] args) {
        testNullArrayThrows();
        testNegativeOffsetThrows();
        testNegativeLengthThrows();
        testOffsetLengthOutOfBoundsThrows();
        testUpdateComputesConsistentValue();
    }

    private static void testNullArrayThrows() {
        CRC32C crc32c = new CRC32C();
        try {
            crc32c.update(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNegativeOffsetThrows() {
        CRC32C crc32c = new CRC32C();
        byte[] data = new byte[10];
        try {
            crc32c.update(data, -1, 1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testNegativeLengthThrows() {
        CRC32C crc32c = new CRC32C();
        byte[] data = new byte[10];
        try {
            crc32c.update(data, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testOffsetLengthOutOfBoundsThrows() {
        CRC32C crc32c = new CRC32C();
        byte[] data = new byte[10];
        try {
            crc32c.update(data, 5, 6); // 5 + 6 = 11 > data.length
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for offset+length > array length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testUpdateComputesConsistentValue() {
        CRC32C crc32c = new CRC32C();
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        crc32c.update(data, 0, data.length);
        long value1 = crc32c.getValue();

        CRC32C crc32c2 = new CRC32C();
        crc32c2.update(data, 0, 2);
        crc32c2.update(data, 2, 3);
        long value2 = crc32c2.getValue();

        if (value1 != value2) {
            throw new RuntimeException("CRC32C values differ for split vs single update");
        }
    }
}
