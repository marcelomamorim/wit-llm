/*
 * @test
 * @summary Behavioral tests for BufferedInputStream.skip(long) using public APIs only
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testZeroAndNegativeSkip();
        testSkipFromBuffer();
        testSkipBeyondBufferedData();
        testSkipUnbufferedDelegates();
        testSkipKeepsMarkForReset();
        testSkipAtEof();
        testSkipAfterCloseThrows();
    }

    private static byte[] data() {
        byte[] b = new byte[10];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) i;
        }
        return b;
    }

    private static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    private static void testZeroAndNegativeSkip() throws IOException {
        BufferedInputStream b = new BufferedInputStream(new ByteArrayInputStream(data()));
        check(b.skip(0) == 0, "skip(0) should return 0");
        check(b.skip(-5) == 0, "skip(negative) should return 0");
        check(b.read() == 0, "no bytes should be consumed by non-positive skip");
    }

    private static void testSkipFromBuffer() throws IOException {
        BufferedInputStream b = new BufferedInputStream(new ByteArrayInputStream(data()), 16);
        b.read();
        long s = b.skip(3);
        check(s == 3, "skip(3) from buffered data should return 3, got " + s);
        check(b.read() == 4, "next byte after skip(3) should be 4");
    }

    private static void testSkipBeyondBufferedData() throws IOException {
        BufferedInputStream b = new BufferedInputStream(new ByteArrayInputStream(data()), 16);
        b.read();
        long s = b.skip(100);
        check(s == 9, "skip beyond buffered data should return remaining buffered bytes, got " + s);
        check(b.read() == -1, "stream should be at EOF after skipping all data");
    }

    private static void testSkipUnbufferedDelegates() throws IOException {
        BufferedInputStream b = new BufferedInputStream(new ByteArrayInputStream(data()));
        long s = b.skip(100);
        check(s == 10, "skip without mark should delegate and return 10, got " + s);
        check(b.read() == -1, "stream should be at EOF after delegated skip");
    }

    private static void testSkipKeepsMarkForReset() throws IOException {
        BufferedInputStream b = new BufferedInputStream(new ByteArrayInputStream(data()), 16);
        b.mark(100);
        long s = b.skip(5);
        check(s == 5, "skip(5) with mark set should return 5, got " + s);
        b.reset();
        check(b.read() == 0, "reset after skip should return to marked position");
    }

    private static void testSkipAtEof() throws IOException {
        BufferedInputStream b = new BufferedInputStream(new ByteArrayInputStream(new byte[0]));
        check(b.skip(5) == 0, "skip at EOF should return 0");
    }

    private static void testSkipAfterCloseThrows() throws IOException {
        BufferedInputStream b = new BufferedInputStream(new ByteArrayInputStream(data()));
        b.close();
        boolean thrown = false;
        try {
            b.skip(1);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "skip on closed stream should throw IOException");
    }
}
