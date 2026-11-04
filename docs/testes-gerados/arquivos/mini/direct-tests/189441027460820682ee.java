/*
 * @test
 * @summary Basic tests for Deflater.deflate(byte[], int, int) method
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) throws Exception {
        testDeflateBasic();
        testDeflateWithOffsetAndLength();
        testDeflateWithEmptyInput();
    }

    static void testDeflateBasic() {
        Deflater deflater = new Deflater();
        byte[] input = "Hello, world!".getBytes();
        deflater.setInput(input);
        deflater.finish();
        byte[] output = new byte[100];
        int compressedLength = deflater.deflate(output, 0, output.length);
        if (compressedLength <= 0) {
            throw new RuntimeException("deflate returned non-positive length");
        }
        deflater.end();
    }

    static void testDeflateWithOffsetAndLength() {
        Deflater deflater = new Deflater();
        byte[] input = "Test input for deflater".getBytes();
        deflater.setInput(input);
        deflater.finish();
        byte[] output = new byte[50];
        int off = 5;
        int len = 20;
        int compressedLength = deflater.deflate(output, off, len);
        if (compressedLength < 0 || compressedLength > len) {
            throw new RuntimeException("deflate returned invalid length");
        }
        deflater.end();
    }

    static void testDeflateWithEmptyInput() {
        Deflater deflater = new Deflater();
        deflater.finish();
        byte[] output = new byte[10];
        int compressedLength = deflater.deflate(output, 0, output.length);
        if (compressedLength < 0) {
            throw new RuntimeException("deflate returned negative length on empty input");
        }
        deflater.end();
    }
}
