/* @test    @summary Test deflate method for java.util.zip.Deflater    @run main DeflaterWitupTest */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) {
        testDeflateNormal();
        testDeflateArrayIndexOutOfBounds();
        testDeflateIllegalArgument();
    }

    private static void testDeflateNormal() {
        try {
            Deflater deflater = new Deflater();
            byte[] input = "test data".getBytes("UTF-8");
            byte[] output = new byte[50];
            deflater.setInput(input);
            deflater.finish();
            int bytesCompressed = deflater.deflate(output, 0, output.length, 0);
            if (bytesCompressed < 0 || bytesCompressed > output.length) {
                throw new AssertionError("Unexpected compressed size: " + bytesCompressed);
            }
            deflater.end();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal deflate", e);
        }
    }

    private static void testDeflateArrayIndexOutOfBounds() {
        try {
            Deflater deflater = new Deflater();
            byte[] input = "test".getBytes();
            byte[] output = new byte[10];
            deflater.setInput(input);
            deflater.finish();
            // Intentionally passing invalid offset and length to trigger exception
            deflater.deflate(output, -1, 5, 0);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testDeflateIllegalArgument() {
        try {
            Deflater deflater = new Deflater();
            byte[] input = "test".getBytes();
            byte[] output = new byte[10];
            deflater.setInput(input);
            deflater.finish();
            // Passing invalid flush value to trigger exception
            deflater.deflate(output, 0, 4, 5);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
