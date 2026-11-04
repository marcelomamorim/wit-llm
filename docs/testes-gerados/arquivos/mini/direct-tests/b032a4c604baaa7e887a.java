/*
 * @test
 * @summary Test java.util.zip.InflaterInputStream.skip(long) method
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testSkipNegative();
        testSkipZero();
        testSkipMoreThanAvailable();
        testSkipPartial();
        testSkipAfterClose();
    }

    private static void testSkipNegative() {
        try {
            InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(new byte[0]));
            iis.skip(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative skip length");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testSkipZero() throws IOException {
        byte[] compressed = new byte[10]; // empty or invalid compressed data
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressed));
        long skipped = iis.skip(0L);
        if (skipped != 0L) {
            throw new RuntimeException("Expected skip(0) to return 0 but got " + skipped);
        }
        iis.close();
    }

    private static void testSkipMoreThanAvailable() throws IOException {
        byte[] compressed = new byte[10];
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressed));
        long skipped = iis.skip(1000L);
        if (skipped < 0 || skipped > 1000L) {
            throw new RuntimeException("skip returned invalid value " + skipped);
        }
        iis.close();
    }

    private static void testSkipPartial() throws IOException {
        byte[] compressed = new byte[20];
        for (int i = 0; i < compressed.length; i++) {
            compressed[i] = (byte) i;
        }
        InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressed));
        long toSkip = 5L;
        long skipped = iis.skip(toSkip);
        if (skipped != toSkip) {
            throw new RuntimeException("Expected to skip " + toSkip + " but skipped " + skipped);
        }
        iis.close();
    }

    private static void testSkipAfterClose() {
        try {
            InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(new byte[10]));
            iis.close();
            iis.skip(1L);
            throw new RuntimeException("Expected IOException after skip on closed stream");
        } catch (IOException e) {
            // expected
        }
    }
}
