/* @test    @summary Test deflate method for normal and exceptional behavior    @run main DeflaterWitupTest */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) {
        testDeflateNormal();
        testDeflateWithInvalidOffset();
        testDeflateWithInvalidLength();
    }

    private static void testDeflateNormal() {
        try {
            Deflater deflater = new Deflater();
            byte[] input = "test data".getBytes();
            deflater.setInput(input);
            deflater.finish();
            byte[] output = new byte[50];
            int compressedLength = deflater.deflate(output, 0, output.length);
            if (compressedLength < 0 || compressedLength > output.length) {
                throw new AssertionError("Unexpected compressed length: " + compressedLength);
            }
            deflater.end();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal deflate", e);
        }
    }

    private static void testDeflateWithInvalidOffset() {
        try {
            Deflater deflater = new Deflater();
            byte[] input = "test data".getBytes();
            deflater.setInput(input);
            deflater.finish();
            byte[] output = new byte[50];
            // offset negative
            int result = deflater.deflate(output, -1, output.length);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative offset, but got: " + result);
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative offset", e);
        }
        try {
            Deflater deflater = new Deflater();
            byte[] input = "test data".getBytes();
            deflater.setInput(input);
            deflater.finish();
            byte[] output = new byte[50];
            // offset beyond array length
            int result = deflater.deflate(output, output.length + 1, output.length);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for offset beyond array, but got: " + result);
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for offset beyond array", e);
        }
    }

    private static void testDeflateWithInvalidLength() {
        try {
            Deflater deflater = new Deflater();
            byte[] input = "test data".getBytes();
            deflater.setInput(input);
            deflater.finish();
            byte[] output = new byte[50];
            // length negative
            int result = deflater.deflate(output, 0, -1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative length, but got: " + result);
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative length", e);
        }
        try {
            Deflater deflater = new Deflater();
            byte[] input = "test data".getBytes();
            deflater.setInput(input);
            deflater.finish();
            byte[] output = new byte[50];
            // length beyond array size
            int result = deflater.deflate(output, 0, output.length + 1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for length beyond array, but got: " + result);
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for length beyond array", e);
        }
    }
}
