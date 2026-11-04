/* @test    @summary Test Deflater.deflate(byte[], int, int) method for normal operation and boundary conditions    @run main DeflaterWitupTest */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) {
        testDeflateNormal();
        testDeflateZeroLength();
        testDeflateFullBuffer();
        testDeflateExceedBuffer();
    }

    static void testDeflateNormal() {
        byte[] input = "testdata".getBytes();
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        byte[] output = new byte[20];
        int compressedLength = deflater.deflate(output, 0, output.length);
        assert compressedLength > 0 : "Compressed length should be positive";
        deflater.end();
    }

    static void testDeflateZeroLength() {
        byte[] input = new byte[0];
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        byte[] output = new byte[10];
        int compressedLength = deflater.deflate(output, 0, output.length);
        assert compressedLength == 0 : "Compressed length should be zero for empty input";
        deflater.end();
    }

    static void testDeflateFullBuffer() {
        byte[] input = "abc".getBytes();
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        byte[] output = new byte[3];
        int compressedLength = deflater.deflate(output, 0, output.length);
        assert compressedLength <= output.length : "Compressed length should not exceed output buffer";
        deflater.end();
    }

    static void testDeflateExceedBuffer() {
        byte[] input = "longerinputdata".getBytes();
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();
        byte[] output = new byte[5];
        int compressedLength = deflater.deflate(output, 0, output.length);
        assert compressedLength <= output.length : "Compressed length should not exceed output buffer";
        deflater.end();
    }
}
