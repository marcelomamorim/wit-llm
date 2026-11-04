/*
 * @test
 * @summary Witup-derived tests for InflaterInputStream.skip(long) argument validation, closed-stream behavior and observable skip counts
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static byte[] compress(byte[] data) {
        Deflater d = new Deflater();
        d.setInput(data);
        d.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (!d.finished()) {
            int n = d.deflate(buf);
            bos.write(buf, 0, n);
        }
        d.end();
        return bos.toByteArray();
    }

    static void testNegativeSkipThrows() throws IOException {
        byte[] comp = compress(new byte[]{1, 2, 3});
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(comp));
        boolean thrown = false;
        try {
            in.skip(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "skip(-1) must throw IllegalArgumentException");
        in.close();
    }

    static void testNegativeSkipThrowsEvenWhenClosed() throws IOException {
        byte[] comp = compress(new byte[]{1, 2, 3});
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(comp));
        in.close();
        boolean thrown = false;
        try {
            in.skip(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "skip(negative) must check argument before stream state");
    }

    static void testSkipOnClosedStreamThrowsIOException() throws IOException {
        byte[] comp = compress(new byte[]{1, 2, 3});
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(comp));
        in.close();
        boolean thrown = false;
        try {
            in.skip(1L);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "skip(n>=0) on closed stream must throw IOException");
    }

    static void testSkipZeroReturnsZero() throws IOException {
        byte[] data = {10, 20, 30};
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(compress(data)));
        check(in.skip(0L) == 0L, "skip(0) must return 0");
        check(in.read() == 10, "stream must not advance after skip(0)");
        in.close();
    }

    static void testSkipReturnsCountAndPositionsStream() throws IOException {
        byte[] data = new byte[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i + 1);
        }
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(compress(data)));
        long skipped = in.skip(5L);
        check(skipped == 5L, "skip(5) must return 5, got " + skipped);
        check(in.read() == data[5], "next byte after skip(5) must be data[5]");
        in.close();
    }

    static void testSkipPastEndReturnsAvailable() throws IOException {
        byte[] data = {1, 2, 3, 4};
        InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(compress(data)));
        long skipped = in.skip(1000L);
        check(skipped == data.length, "skip past end must return available count, got " + skipped);
        check(in.skip(10L) == 0L, "skip at EOF must return 0");
        check(in.read() == -1, "read at EOF must return -1");
        in.close();
    }

    public static void main(String[] args) throws IOException {
        testNegativeSkipThrows();
        testNegativeSkipThrowsEvenWhenClosed();
        testSkipOnClosedStreamThrowsIOException();
        testSkipZeroReturnsZero();
        testSkipReturnsCountAndPositionsStream();
        testSkipPastEndReturnsAvailable();
    }
}
