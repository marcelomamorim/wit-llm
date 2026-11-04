/*
 * @test
 * @summary Test java.util.zip.Adler32.update(byte[], int, int) for valid and invalid inputs
 * @run main Adler32WitupTest
 */

import java.util.zip.Adler32;

public class Adler32WitupTest {
    public static void main(String[] args) {
        testNullArrayThrowsNPE();
        testNegativeOffsetThrowsAIOOBE();
        testNegativeLengthThrowsAIOOBE();
        testOffsetLengthOutOfBoundsThrowsAIOOBE();
        testValidUpdateChangesValue();
    }

    private static void testNullArrayThrowsNPE() {
        Adler32 adler = new Adler32();
        try {
            adler.update(null, 0, 1);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNegativeOffsetThrowsAIOOBE() {
        Adler32 adler = new Adler32();
        byte[] b = new byte[10];
        try {
            adler.update(b, -1, 5);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for negative offset");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testNegativeLengthThrowsAIOOBE() {
        Adler32 adler = new Adler32();
        byte[] b = new byte[10];
        try {
            adler.update(b, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for negative length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testOffsetLengthOutOfBoundsThrowsAIOOBE() {
        Adler32 adler = new Adler32();
        byte[] b = new byte[10];
        try {
            adler.update(b, 6, 5); // 6 + 5 = 11 > b.length
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown for offset+len > array length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testValidUpdateChangesValue() {
        Adler32 adler = new Adler32();
        byte[] b = {1, 2, 3, 4, 5};
        long before = adler.getValue();
        adler.update(b, 1, 3); // bytes 2,3,4
        long after = adler.getValue();
        if (before == after) {
            throw new RuntimeException("Adler32 value did not change after update");
        }
    }
}
