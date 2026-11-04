/*
 * @test
 * @summary Basic jtreg tests for Deflater.deflate(byte[], int, int, int) method
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) throws Exception {
        testInvalidOffsets();
        testInvalidFlush();
        testDeflateBasic();
        testDeflateWithFinish();
        testDeflateWithSetParamsFlag();
    }

    static void testInvalidOffsets() {
        Deflater def = new Deflater();
        byte[] output = new byte[10];
        def.setInput(new byte[] {1, 2, 3});
        def.finish();
        try {
            def.deflate(output, -1, 5, Deflater.NO_FLUSH);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative offset");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
        try {
            def.deflate(output, 0, -1, Deflater.NO_FLUSH);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
        try {
            def.deflate(output, 6, 5, Deflater.NO_FLUSH); // 6 + 5 > 10
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for offset+len > output.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }

    static void testInvalidFlush() {
        Deflater def = new Deflater();
        byte[] output = new byte[10];
        def.setInput(new byte[] {1, 2, 3});
        def.finish();
        try {
            def.deflate(output, 0, 10, 12345); // invalid flush value
            throw new RuntimeException("Expected IllegalArgumentException for invalid flush value");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testDeflateBasic() {
        Deflater def = new Deflater();
        byte[] input = "hello world".getBytes();
        def.setInput(input);
        def.finish();
        byte[] output = new byte[100];
        int written = def.deflate(output, 0, output.length, Deflater.NO_FLUSH);
        if (written <= 0) {
            throw new RuntimeException("Expected some bytes to be written");
        }
    }

    static void testDeflateWithFinish() {
        Deflater def = new Deflater();
        byte[] input = "test finish flush".getBytes();
        def.setInput(input);
        def.finish();
        byte[] output = new byte[100];
        int written = def.deflate(output, 0, output.length, Deflater.SYNC_FLUSH);
        if (written <= 0) {
            throw new RuntimeException("Expected some bytes to be written even with SYNC_FLUSH when finish is set");
        }
    }

    static void testDeflateWithSetParamsFlag() {
        Deflater def = new Deflater();
        byte[] input = "param test".getBytes();
        def.setInput(input);
        def.finish();
        // forcibly set setParams true by reflection is not allowed, so test normal path
        byte[] output = new byte[100];
        int written = def.deflate(output, 0, output.length, Deflater.NO_FLUSH);
        if (written < 0) {
            throw new RuntimeException("deflate returned negative bytes written");
        }
    }
}
