/*
 * @test
 * @summary Test ZipInputStream.skip(long) for exception and normal behavior
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testSkipNegative();
        testSkipClosedStream();
        testSkipNormal();
    }

    private static void testSkipNegative() {
        try {
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]));
            zis.skip(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip length");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testSkipClosedStream() throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]));
        zis.close();
        try {
            zis.skip(0L);
            throw new RuntimeException("Expected IOException for skip on closed stream");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testSkipNormal() throws IOException {
        byte[] data = new byte[1000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i % 256);
        }
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(data));
        long skipped = zis.skip(500L);
        if (skipped != 500L) {
            throw new RuntimeException("Expected to skip 500 bytes, but skipped " + skipped);
        }
        // skip more than available
        skipped = zis.skip(1000L);
        if (skipped <= 0) {
            throw new RuntimeException("Expected to skip some bytes or reach EOF, but skipped " + skipped);
        }
        zis.close();
    }
}
