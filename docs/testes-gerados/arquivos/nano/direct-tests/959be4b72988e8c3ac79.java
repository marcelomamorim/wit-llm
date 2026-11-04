/* @test    @summary Test CRC32.update(byte[] b, int off, int len) method for correct behavior and exception handling    @run main CRC32WitupTest */

import java.util.zip.CRC32;

public class CRC32WitupTest {

    public static void main(String[] args) {
        testUpdateWithValidInput();
        testUpdateWithNullArray();
        testUpdateWithNegativeOffset();
        testUpdateWithNegativeLength();
        testUpdateWithOffsetExceedingArray();
    }

    static void testUpdateWithValidInput() {
        CRC32 crc = new CRC32();
        byte[] data = {1, 2, 3, 4, 5};
        crc.reset();
        crc.update(data, 1, 3); // should process bytes 2, 3, 4
        long checksum = crc.getValue();
        // Check that checksum is not zero (initial value) and is a valid CRC
        if (checksum == 0) {
            throw new AssertionError("Checksum should not be zero after valid update");
        }
    }

    static void testUpdateWithNullArray() {
        CRC32 crc = new CRC32();
        boolean exceptionThrown = false;
        try {
            crc.update(null, 0, 1);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException expected when array is null");
        }
    }

    static void testUpdateWithNegativeOffset() {
        CRC32 crc = new CRC32();
        byte[] data = {1, 2, 3};
        boolean exceptionThrown = false;
        try {
            crc.update(data, -1, 2);
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("ArrayIndexOutOfBoundsException expected for negative offset");
        }
    }

    static void testUpdateWithNegativeLength() {
        CRC32 crc = new CRC32();
        byte[] data = {1, 2, 3};
        boolean exceptionThrown = false;
        try {
            crc.update(data, 0, -1);
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("ArrayIndexOutOfBoundsException expected for negative length");
        }
    }

    static void testUpdateWithOffsetExceedingArray() {
        CRC32 crc = new CRC32();
        byte[] data = {1, 2, 3};
        boolean exceptionThrown = false;
        try {
            crc.update(data, 2, 2); // off + len = 4 > data.length
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("ArrayIndexOutOfBoundsException expected when off + len exceeds array length");
        }
    }
}
