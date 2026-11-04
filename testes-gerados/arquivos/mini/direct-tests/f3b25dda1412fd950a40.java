/*
 * @test
 * @summary Test java.util.zip.Inflater.setInput(byte[], int, int) for valid and invalid inputs
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;

public class InflaterWitupTest {
    public static void main(String[] args) {
        testValidSetInput();
        testSetInputNegativeOffset();
        testSetInputNegativeLength();
        testSetInputOffsetLengthOutOfBounds();
    }

    private static void testValidSetInput() {
        Inflater inflater = new Inflater();
        byte[] data = new byte[10];
        // Fill with some data
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        // Use full array
        inflater.setInput(data, 0, data.length);
        // Use partial array
        inflater.setInput(data, 2, 5);
    }

    private static void testSetInputNegativeOffset() {
        Inflater inflater = new Inflater();
        byte[] data = new byte[5];
        try {
            inflater.setInput(data, -1, 3);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testSetInputNegativeLength() {
        Inflater inflater = new Inflater();
        byte[] data = new byte[5];
        try {
            inflater.setInput(data, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testSetInputOffsetLengthOutOfBounds() {
        Inflater inflater = new Inflater();
        byte[] data = new byte[5];
        try {
            inflater.setInput(data, 3, 3); // 3 + 3 = 6 > 5
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for offset + length > input.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
