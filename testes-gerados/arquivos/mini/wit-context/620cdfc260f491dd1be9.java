/*
 * @test
 * @summary Test java.util.zip.Deflater.setInput(byte[], int, int) for valid and invalid inputs
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) {
        testSetInputValid();
        testSetInputInvalidNegativeOffset();
        testSetInputInvalidNegativeLength();
        testSetInputInvalidOffsetLengthOverflow();
    }

    private static void testSetInputValid() {
        Deflater deflater = new Deflater();
        byte[] input = new byte[10];
        try {
            deflater.setInput(input, 0, 10); // full array
            deflater.setInput(input, 5, 5);  // partial array
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid input", e);
        } finally {
            deflater.end();
        }
    }

    private static void testSetInputInvalidNegativeOffset() {
        Deflater deflater = new Deflater();
        byte[] input = new byte[10];
        try {
            deflater.setInput(input, -1, 5);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }

    private static void testSetInputInvalidNegativeLength() {
        Deflater deflater = new Deflater();
        byte[] input = new byte[10];
        try {
            deflater.setInput(input, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }

    private static void testSetInputInvalidOffsetLengthOverflow() {
        Deflater deflater = new Deflater();
        byte[] input = new byte[10];
        try {
            deflater.setInput(input, 5, 6); // 5 + 6 = 11 > input.length
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for offset+length overflow");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }
}
