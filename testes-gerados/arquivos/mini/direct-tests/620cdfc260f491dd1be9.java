/*
 * @test
 * @summary Test java.util.zip.Deflater.setInput(byte[], int, int) for valid and invalid inputs
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) {
        testValidSetInput();
        testSetInputWithNegativeOffset();
        testSetInputWithNegativeLength();
        testSetInputWithOffsetLengthOutOfBounds();
    }

    private static void testValidSetInput() {
        Deflater deflater = new Deflater();
        byte[] input = new byte[10];
        // Fill input with some data
        for (int i = 0; i < input.length; i++) {
            input[i] = (byte) i;
        }
        // Use valid offset and length
        deflater.setInput(input, 2, 5);
        // No exception expected, test passes if no exception thrown
    }

    private static void testSetInputWithNegativeOffset() {
        Deflater deflater = new Deflater();
        byte[] input = new byte[10];
        try {
            deflater.setInput(input, -1, 5);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Expected exception
        }
    }

    private static void testSetInputWithNegativeLength() {
        Deflater deflater = new Deflater();
        byte[] input = new byte[10];
        try {
            deflater.setInput(input, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Expected exception
        }
    }

    private static void testSetInputWithOffsetLengthOutOfBounds() {
        Deflater deflater = new Deflater();
        byte[] input = new byte[10];
        try {
            // offset + length > input.length
            deflater.setInput(input, 6, 5);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for offset + length > input.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Expected exception
        }
    }
}
