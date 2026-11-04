/*
 * @test
 * @summary Tests CRC32.update(byte[], int, int) argument validation and checksum values
 * @run main CRC32WitupTest
 */

import java.util.zip.CRC32;

public class CRC32WitupTest {

    public static void main(String[] args) {
        testNullArrayThrowsNPE();
        testNegativeOffsetThrowsAIOOBE();
        testNegativeLengthThrowsAIOOBE();
        testOffsetPlusLengthOutOfBoundsThrowsAIOOBE();
        testValidUpdateProducesKnownChecksum();
        testFailedUpdateDoesNotChangeChecksum();
        testZeroLengthAtEndIsAccepted();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArrayThrowsNPE() {
        CRC32 crc = new CRC32();
        boolean thrown = false;
        try {
            crc.update(null, 0, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }

    static void testNegativeOffsetThrowsAIOOBE() {
        CRC32 crc = new CRC32();
        byte[] data = {'1', '2', '3'};
        boolean thrown = false;
        try {
            crc.update(data, -1, 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for negative offset");
    }

    static void testNegativeLengthThrowsAIOOBE() {
        CRC32 crc = new CRC32();
        byte[] data = {'1', '2', '3'};
        boolean thrown = false;
        try {
            crc.update(data, 0, -1);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for negative length");
    }

    static void testOffsetPlusLengthOutOfBoundsThrowsAIOOBE() {
        CRC32 crc = new CRC32();
        byte[] data = {'1', '2', '3'};
        boolean thrown = false;
        try {
            crc.update(data, 2, 2);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException when off+len exceeds array length");
    }

    static void testValidUpdateProducesKnownChecksum() {
        CRC32 crc = new CRC32();
        byte[] data = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        crc.update(data, 0, data.length);
        check(crc.getValue() == 0xCBF43926L,
              "unexpected CRC-32 of '123456789': " + Long.toHexString(crc.getValue()));
    }

    static void testFailedUpdateDoesNotChangeChecksum() {
        CRC32 crc = new CRC32();
        byte[] data = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        crc.update(data, 0, data.length);
        long before = crc.getValue();
        boolean thrown = false;
        try {
            crc.update(data, 5, 10);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for out-of-range update");
        check(crc.getValue() == before,
              "checksum changed after rejected update");
    }

    static void testZeroLengthAtEndIsAccepted() {
        CRC32 crc = new CRC32();
        byte[] data = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        crc.update(data, 0, data.length);
        long before = crc.getValue();
        crc.update(data, data.length, 0);
        check(crc.getValue() == before,
              "zero-length update at end of array changed checksum");
    }
}
