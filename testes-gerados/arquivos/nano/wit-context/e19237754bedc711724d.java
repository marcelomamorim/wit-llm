/*
 * @test
 * @summary Tests for CRC32C.update(byte[], int, int) method, covering null and bounds exceptions.
 * @run main CRC32CWitupTest
 */

import java.util.zip.CRC32C;

public class CRC32CWitupTest {

    public static void main(String[] args) {
        testUpdateNullArray();
        testUpdateArrayIndexOutOfBounds();
    }

    private static void testUpdateNullArray() {
        CRC32C crc = new CRC32C();
        try {
            crc.update(null, 0, 1);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testUpdateArrayIndexOutOfBounds() {
        CRC32C crc = new CRC32C();
        byte[] data = {0, 1, 2, 3};
        // off + len > data.length
        try {
            crc.update(data, 2, 3);
            System.err.println("Failed: Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
        // off < 0
        try {
            crc.update(data, -1, 2);
            System.err.println("Failed: Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
        // len < 0
        try {
            crc.update(data, 1, -1);
            System.err.println("Failed: Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
        // off > b.length - len
        try {
            crc.update(data, 3, 2);
            System.err.println("Failed: Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
