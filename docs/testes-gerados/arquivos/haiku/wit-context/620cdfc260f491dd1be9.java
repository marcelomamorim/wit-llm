/*
 * @test
 * @summary Verifies that Deflater.setInput(byte[], int, int) rejects invalid off/len ranges and accepts valid ones.
 * @run main DeflaterWitupTest
 */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] input = new byte[10];
        Deflater d = new Deflater();
        try {
            expectAioobe(d, input, -1, 5);
            expectAioobe(d, input, 0, -1);
            expectAioobe(d, input, 6, 5);
            expectAioobe(d, input, 11, 0);
            expectAioobe(d, input, 0, 11);

            d.setInput(input, 0, 10);
            d.setInput(input, 10, 0);
            d.setInput(input, 5, 5);

            Deflater d2 = new Deflater();
            try {
                d2.setInput(input, 2, 4);
                d2.finish();
                byte[] out = new byte[100];
                while (!d2.finished()) {
                    d2.deflate(out);
                }
                if (d2.getBytesRead() != 4) {
                    throw new RuntimeException("Expected 4 bytes read, got " + d2.getBytesRead());
                }
            } finally {
                d2.end();
            }
        } finally {
            d.end();
        }
    }

    private static void expectAioobe(Deflater d, byte[] input, int off, int len) {
        try {
            d.setInput(input, off, len);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for off=" + off + ", len=" + len);
    }
}
