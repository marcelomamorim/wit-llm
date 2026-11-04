/*
 * @test
 * @summary Deterministic tests for java.util.zip.CRC32C.update(byte[], int, int) method
 * @run main CRC32CWitupTest
 */

import java.util.zip.CRC32C;

public class CRC32CWitupTest {
    public static void main(String[] args) {
        testNullArrayThrowsNPE();
        testNegativeOffsetThrowsAIOOBE();
        testNegativeLengthThrowsAIOOBE();
        testOffsetLengthOutOfBoundsThrowsAIOOBE();
        testValidUpdateComputesConsistentValue();
    }

    private static void testNullArrayThrowsNPE() {
        CRC32C crc = new CRC32C();
        try {
            crc.update(null, 0, 0);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNegativeOffsetThrowsAIOOBE() {
        CRC32C crc = new CRC32C();
        byte[] data = new byte[10];
        try {
            crc.update(data, -1, 5);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testNegativeLengthThrowsAIOOBE() {
        CRC32C crc = new CRC32C();
        byte[] data = new byte[10];
        try {
            crc.update(data, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testOffsetLengthOutOfBoundsThrowsAIOOBE() {
        CRC32C crc = new CRC32C();
        byte[] data = new byte[10];
        try {
            crc.update(data, 5, 6); // 5 + 6 = 11 > data.length
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for offset+length out of bounds");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testValidUpdateComputesConsistentValue() {
        CRC32C crc1 = new CRC32C();
        CRC32C crc2 = new CRC32C();
        byte[] data = new byte[] {1, 2, 3, 4, 5};

        crc1.update(data, 0, data.length);
        long val1 = crc1.getValue();

        // Update in two parts
        crc2.update(data, 0, 2);
        crc2.update(data, 2, 3);
        long val2 = crc2.getValue();

        if (val1 != val2) {
            throw new RuntimeException("CRC values differ for split vs full update: " + val1 + " vs " + val2);
        }

        // Update empty range should not change value
        CRC32C crc3 = new CRC32C();
        crc3.update(data, 0, 0);
        if (crc3.getValue() != 0) {
            throw new RuntimeException("CRC value should be 0 after empty update");
        }
    }
}
