/*
 * @test
 * @summary Witup-derived checks for Deflater.deflate argument validation and basic behavior
 * @run main DeflaterWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] out = new byte[10];

        expectAIOOBE(out, -1, 5, Deflater.NO_FLUSH);
        expectAIOOBE(out, 0, -1, Deflater.NO_FLUSH);
        expectAIOOBE(out, 5, 6, Deflater.NO_FLUSH);
        expectAIOOBE(out, 11, 0, Deflater.NO_FLUSH);

        expectIAE(out, 0, 10, 1);
        expectIAE(out, 0, 10, 4);
        expectIAE(out, 0, 10, -1);

        Deflater empty = new Deflater();
        try {
            int n = empty.deflate(out, out.length, 0, Deflater.NO_FLUSH);
            if (n != 0) {
                throw new RuntimeException("expected 0 bytes written, got " + n);
            }
        } finally {
            empty.end();
        }

        Deflater d = new Deflater();
        try {
            byte[] input = "blahblahblah".getBytes(StandardCharsets.UTF_8);
            d.setInput(input);
            d.finish();
            byte[] buf = new byte[100];
            int total = 0;
            while (!d.finished()) {
                total += d.deflate(buf, 0, buf.length);
            }
            if (total <= 0) {
                throw new RuntimeException("expected compressed output");
            }
            if (!d.finished()) {
                throw new RuntimeException("expected finished()");
            }
            if (d.getBytesRead() != input.length) {
                throw new RuntimeException("bytesRead mismatch: " + d.getBytesRead());
            }
            if (d.getBytesWritten() != total) {
                throw new RuntimeException("bytesWritten mismatch: " + d.getBytesWritten());
            }
        } finally {
            d.end();
        }
    }

    static void expectAIOOBE(byte[] out, int off, int len, int flush) {
        Deflater d = new Deflater();
        boolean thrown = false;
        try {
            d.deflate(out, off, len, flush);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        } finally {
            d.end();
        }
        if (!thrown) {
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException for off=" + off
                    + " len=" + len);
        }
    }

    static void expectIAE(byte[] out, int off, int len, int flush) {
        Deflater d = new Deflater();
        boolean thrown = false;
        try {
            d.deflate(out, off, len, flush);
        } catch (IllegalArgumentException e) {
            thrown = true;
        } finally {
            d.end();
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for flush=" + flush);
        }
    }
}
