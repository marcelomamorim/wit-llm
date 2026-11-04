/*
 * @test
 * @summary Tests for java.util.zip.CRC32.update(byte[], int, int)
 * @run main CRC32WitupTest
 */

import java.util.zip.CRC32;

public class CRC32WitupTest {

    public static void main(String[] args) {
        testUpdateWithNullByteArray();
        testUpdateWithInvalidOffsetAndLength();
    }

    public static void testUpdateWithNullByteArray() {
        CRC32 crc = new CRC32();
        try {
            crc.update((byte[])null, 0, 1);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    public static void testUpdateWithInvalidOffsetAndLength() {
        CRC32 crc = new CRC32();
        byte[] data = {1, 2, 3, 4, 5};
        // off + len > data.length
        try {
            crc.update(data, 3, 3);
            System.out.println("FAIL: Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            System.out.println("PASS: ArrayIndexOutOfBoundsException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
        // negative off
        try {
            crc.update(data, -1, 2);
            System.out.println("FAIL: Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            System.out.println("PASS: ArrayIndexOutOfBoundsException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
        // negative len
        try {
            crc.update(data, 1, -1);
            System.out.println("FAIL: Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException expected) {
            System.out.println("PASS: ArrayIndexOutOfBoundsException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
