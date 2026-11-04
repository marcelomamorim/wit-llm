/* @test @summary Basic test for Deflater.deflate method @run main DeflaterWitupTest */

import java.util.zip.Deflater;

public class DeflaterWitupTest {
    public static void main(String[] args) {
        testDeflateBasic();
        testDeflateWithEmptyInput();
        testDeflateWithMaxLength();
        testDeflateAfterFinish();
    }

    static void testDeflateBasic() {
        try {
            Deflater deflater = new Deflater();
            byte[] input = "test".getBytes("UTF-8");
            deflater.setInput(input);
            deflater.finish();
            byte[] output = new byte[20];
            int bytesCompressed = deflater.deflate(output, 0, output.length, Deflater.NO_FLUSH);
            assert bytesCompressed >= 0 : "Bytes compressed should be non-negative";
            deflater.end();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testDeflateBasic", e);
        }
    }

    static void testDeflateWithEmptyInput() {
        try {
            Deflater deflater = new Deflater();
            byte[] input = new byte[0];
            deflater.setInput(input);
            deflater.finish();
            byte[] output = new byte[10];
            int bytesCompressed = deflater.deflate(output, 0, output.length, Deflater.NO_FLUSH);
            assert bytesCompressed >= 0 : "Bytes compressed should be non-negative";
            deflater.end();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testDeflateWithEmptyInput", e);
        }
    }

    static void testDeflateWithMaxLength() {
        try {
            Deflater deflater = new Deflater();
            byte[] input = "another test string".getBytes("UTF-8");
            deflater.setInput(input);
            deflater.finish();
            byte[] output = new byte[5];
            int bytesCompressed = deflater.deflate(output, 0, output.length, Deflater.NO_FLUSH);
            assert bytesCompressed >= 0 : "Bytes compressed should be non-negative";
            deflater.end();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testDeflateWithMaxLength", e);
        }
    }

    static void testDeflateAfterFinish() {
        try {
            Deflater deflater = new Deflater();
            byte[] input = "test after finish".getBytes("UTF-8");
            deflater.setInput(input);
            deflater.finish();
            byte[] output = new byte[50];
            deflater.deflate(output, 0, output.length, Deflater.NO_FLUSH);
            // After finish, deflate should still work but may produce 0 bytes if no input left
            int result = deflater.deflate(output, 0, output.length, Deflater.NO_FLUSH);
            assert result >= 0 : "Result should be non-negative";
            deflater.end();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testDeflateAfterFinish", e);
        }
    }
}