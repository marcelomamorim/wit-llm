/*
 * @test
 * @summary Test for Adler32.update(byte[], int, int) method, covering null and bounds exceptions
 * @run main Adler32WitupTest
 */

import java.util.zip.Adler32;

public class Adler32WitupTest {

    public static void main(String[] args) {
        testUpdateWithNullArray();
        testUpdateWithNegativeOffset();
        testUpdateWithNegativeLength();
        testUpdateWithOffsetExceedingArray();
    }

    static void testUpdateWithNullArray() {
        Adler32 adler = new Adler32();
        try {
            adler.update(null, 0, 1);
            System.err.println("Failed: NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testUpdateWithNegativeOffset() {
        Adler32 adler = new Adler32();
        byte[] data = {1, 2, 3};
        try {
            adler.update(data, -1, 2);
            System.err.println("Failed: ArrayIndexOutOfBoundsException not thrown for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Expected exception
        }
    }

    static void testUpdateWithNegativeLength() {
        Adler32 adler = new Adler32();
        byte[] data = {1, 2, 3};
        try {
            adler.update(data, 0, -1);
            System.err.println("Failed: ArrayIndexOutOfBoundsException not thrown for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Expected exception
        }
    }

    static void testUpdateWithOffsetExceedingArray() {
        Adler32 adler = new Adler32();
        byte[] data = {1, 2, 3};
        try {
            adler.update(data, 2, 2);
            System.err.println("Failed: ArrayIndexOutOfBoundsException not thrown for offset + len exceeding array length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Expected exception
        }
    }
}
