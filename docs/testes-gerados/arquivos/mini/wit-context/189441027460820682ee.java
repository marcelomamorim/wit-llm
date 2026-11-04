/*
 * @test
 * @summary Test Deflater.deflate(byte[], int, int) for valid and invalid input ranges
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) {
        testValidDeflateCall();
        testDeflateWithInvalidOffset();
        testDeflateWithInvalidLength();
        testDeflateWithLengthOverflow();
    }

    private static void testValidDeflateCall() {
        Deflater deflater = new Deflater();
        byte[] input = "test input data".getBytes();
        deflater.setInput(input);
        deflater.finish();
        byte[] output = new byte[100];
        // Valid call: offset=0, length=output.length
        int result = deflater.deflate(output, 0, output.length);
        if (result < 0 || result > output.length) {
            throw new AssertionError("deflate returned invalid length: " + result);
        }
        deflater.end();
    }

    private static void testDeflateWithInvalidOffset() {
        Deflater deflater = new Deflater();
        byte[] output = new byte[10];
        try {
            // offset < 0 should cause ArrayIndexOutOfBoundsException
            deflater.deflate(output, -1, 5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }

    private static void testDeflateWithInvalidLength() {
        Deflater deflater = new Deflater();
        byte[] output = new byte[10];
        try {
            // length < 0 should cause ArrayIndexOutOfBoundsException
            deflater.deflate(output, 0, -1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }

    private static void testDeflateWithLengthOverflow() {
        Deflater deflater = new Deflater();
        byte[] output = new byte[10];
        try {
            // length > output.length - offset should cause ArrayIndexOutOfBoundsException
            deflater.deflate(output, 5, 6); // 6 > 10 - 5
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for length overflow");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            deflater.end();
        }
    }
}
