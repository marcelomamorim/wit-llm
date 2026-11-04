/*
 * @test
 * @summary Tests the skip method of InflaterInputStream for correct behavior and edge cases.
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testSkipNormal();
        testSkipZero();
        testSkipNegative();
        testSkipBeyondEOF();
    }

    private static void testSkipNormal() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        long skipped = iis.skip(3);
        if (skipped != 3) {
            throw new AssertionError("Expected to skip 3 bytes, but skipped " + skipped);
        }
        int remaining = iis.read();
        if (remaining == -1) {
            throw new AssertionError("Expected more data after skip");
        }
    }

    private static void testSkipZero() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        long skipped = iis.skip(0);
        if (skipped != 0) {
            throw new AssertionError("Skipping zero bytes should return 0, but got " + skipped);
        }
    }

    private static void testSkipNegative() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        try {
            iis.skip(-5);
            throw new AssertionError("Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testSkipBeyondEOF() throws IOException {
        byte[] data = {1, 2};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        long skipped = iis.skip(1000);
        if (skipped < 0 || skipped > data.length) {
            throw new AssertionError("Skipped bytes should be between 0 and data length, but got " + skipped);
        }
        long remaining = iis.skip(10);
        if (remaining != 0) {
            throw new AssertionError("Expected to skip 0 bytes at EOF, but skipped " + remaining);
        }
    }
}
