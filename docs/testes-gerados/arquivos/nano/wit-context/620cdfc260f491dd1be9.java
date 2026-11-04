/* @test    @summary Test setInput(byte[] input, int off, int len) for java.util.zip.Deflater    @run main DeflaterWitupTest */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) {
        testSetInputWithValidParameters();
        testSetInputWithNegativeOffset();
        testSetInputWithNegativeLength();
        testSetInputWithOffsetExceedingArray();
    }

    private static void testSetInputWithValidParameters() {
        byte[] input = {1, 2, 3, 4, 5};
        int off = 1;
        int len = 3;
        Deflater deflater = new Deflater();
        deflater.setInput(input, off, len);
        // No exception expected
    }

    private static void testSetInputWithNegativeOffset() {
        byte[] input = {1, 2, 3};
        int off = -1;
        int len = 2;
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(input, off, len);
            fail("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testSetInputWithNegativeLength() {
        byte[] input = {1, 2, 3};
        int off = 0;
        int len = -1;
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(input, off, len);
            fail("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testSetInputWithOffsetExceedingArray() {
        byte[] input = {1, 2, 3};
        int off = 4;
        int len = 1;
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(input, off, len);
            fail("Expected ArrayIndexOutOfBoundsException for offset exceeding array length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void fail(String message) {
        throw new AssertionError(message);
    }
}
