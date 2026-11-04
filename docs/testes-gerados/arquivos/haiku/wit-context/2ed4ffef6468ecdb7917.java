/*
 * @test
 * @summary Witup-generated behavioral checks for BufferedInputStream.skip(long)
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNonPositiveSkipReturnsZero();
        testSkipDelegatesWithoutMark();
        testSkipBoundedByAvailableData();
        testSkipAfterMarkKeepsReadPosition();
        testSkipOnClosedStreamThrowsIOException();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNonPositiveSkipReturnsZero() throws IOException {
        byte[] data = {1, 2, 3};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data), 8);
        check(in.skip(0) == 0, "skip(0) must return 0");
        check(in.skip(-5) == 0, "skip(-5) must return 0");
        check(in.read() == 1, "non-positive skip must not consume bytes");
    }

    static void testSkipDelegatesWithoutMark() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data), 8);
        long skipped = in.skip(2);
        check(skipped == 2, "skip(2) returned " + skipped);
        check(in.read() == 30, "byte after skip(2) should be 30");
    }

    static void testSkipBoundedByAvailableData() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data), 8);
        long skipped = in.skip(100);
        check(skipped == 5, "skip(100) on 5 bytes returned " + skipped);
        check(in.skip(1) == 0, "skip at end of stream must return 0");
        check(in.read() == -1, "stream should be at EOF");
    }

    static void testSkipAfterMarkKeepsReadPosition() throws IOException {
        byte[] data = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(data), 8);
        in.mark(10);
        long skipped = in.skip(3);
        check(skipped == 3, "skip(3) after mark returned " + skipped);
        check(in.read() == 3, "byte after skip(3) after mark should be 3");
    }

    static void testSkipOnClosedStreamThrowsIOException() throws IOException {
        BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(new byte[4]), 8);
        in.close();
        try {
            in.skip(1);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("skip on closed stream must throw IOException");
    }
}
