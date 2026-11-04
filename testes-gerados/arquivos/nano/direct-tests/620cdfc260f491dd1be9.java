/* @test    @summary Test Deflater.setInput(byte[], int, int) method for correct behavior and exception handling    @run main DeflaterWitupTest */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) {
        testValidInput();
        testInvalidInputNegativeOffset();
        testInvalidInputNegativeLength();
        testInvalidInputOffsetExceedsArray();
        testInvalidInputOffsetPlusLengthExceedsArray();
    }

    static void testValidInput() {
        byte[] data = {1, 2, 3, 4, 5};
        Deflater deflater = new Deflater();
        deflater.setInput(data, 1, 3); // should not throw
        // Verify internal state by setting input again and checking no exception
        deflater.setInput(data, 0, data.length); // should not throw
        deflater.end();
    }

    static void testInvalidInputNegativeOffset() {
        byte[] data = {1, 2, 3};
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(data, -1, 2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }

    static void testInvalidInputNegativeLength() {
        byte[] data = {1, 2, 3};
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(data, 0, -1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }

    static void testInvalidInputOffsetExceedsArray() {
        byte[] data = {1, 2, 3};
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(data, 4, 1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for offset exceeding array length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }

    static void testInvalidInputOffsetPlusLengthExceedsArray() {
        byte[] data = {1, 2, 3};
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(data, 2, 2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for offset + length exceeding array length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }
}
