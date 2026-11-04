/*
 * @test
 * @summary Test Deflater.deflate(byte[], int, int, int) for argument validation and normal operation
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) throws Exception {
        testDeflateThrowsArrayIndexOutOfBoundsException();
        testDeflateThrowsIllegalArgumentException();
        testDeflateNormalOperation();
    }

    private static void testDeflateThrowsArrayIndexOutOfBoundsException() {
        Deflater deflater = new Deflater();
        byte[] output = new byte[10];
        deflater.setInput(new byte[] {1, 2, 3});
        deflater.finish();

        // off < 0
        try {
            deflater.deflate(output, -1, 5, 0);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for off < 0");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        // len < 0
        try {
            deflater.deflate(output, 0, -1, 0);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for len < 0");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        // off + len > output.length
        try {
            deflater.deflate(output, 5, 6, 0);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for off + len > output.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        deflater.end();
    }

    private static void testDeflateThrowsIllegalArgumentException() {
        Deflater deflater = new Deflater();
        byte[] output = new byte[10];
        deflater.setInput(new byte[] {1, 2, 3});
        deflater.finish();

        // flush parameter invalid (not 0, 2, or 3)
        int[] invalidFlushValues = {1, 4, -1, Integer.MAX_VALUE};
        for (int flush : invalidFlushValues) {
            try {
                deflater.deflate(output, 0, output.length, flush);
                throw new RuntimeException("Expected IllegalArgumentException for invalid flush value: " + flush);
            } catch (IllegalArgumentException expected) {
            }
        }

        deflater.end();
    }

    private static void testDeflateNormalOperation() {
        Deflater deflater = new Deflater();
        byte[] input = "test input data".getBytes();
        deflater.setInput(input);
        deflater.finish();

        byte[] output = new byte[100];

        // Use valid flush values: NO_FLUSH=0, SYNC_FLUSH=2, FULL_FLUSH=3
        int[] validFlushValues = {0, 2, 3};
        for (int flush : validFlushValues) {
            deflater.reset();
            deflater.setInput(input);
            deflater.finish();
            int written = deflater.deflate(output, 0, output.length, flush);
            if (written < 0 || written > output.length) {
                throw new RuntimeException("deflate returned invalid number of bytes written: " + written);
            }
        }

        deflater.end();
    }
}
