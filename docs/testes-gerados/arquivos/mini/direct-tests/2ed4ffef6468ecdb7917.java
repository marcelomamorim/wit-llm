/*
 * @test
 * @summary Test BufferedInputStream.skip(long) behavior
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testSkipNegativeOrZero();
        testSkipWithinBuffer();
        testSkipMoreThanBuffer();
        testSkipWithMarkposNegative();
    }

    private static void testSkipNegativeOrZero() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data));
        long skipped = bis.skip(0);
        if (skipped != 0) {
            throw new AssertionError("skip(0) should return 0 but returned " + skipped);
        }
        skipped = bis.skip(-10);
        if (skipped != 0) {
            throw new AssertionError("skip(negative) should return 0 but returned " + skipped);
        }
        bis.close();
    }

    private static void testSkipWithinBuffer() throws IOException {
        byte[] data = new byte[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data), 10);
        // Read some bytes to fill buffer
        bis.read(); // pos = 1
        long skipped = bis.skip(5);
        if (skipped != 5) {
            throw new AssertionError("skip(5) within buffer should skip 5 but skipped " + skipped);
        }
        int next = bis.read();
        if (next != 6) {
            throw new AssertionError("Next byte after skip should be 6 but was " + next);
        }
        bis.close();
    }

    private static void testSkipMoreThanBuffer() throws IOException {
        byte[] data = new byte[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data), 10);
        // Skip more than buffer size, forcing skip on underlying stream
        long skipped = bis.skip(15);
        if (skipped != 15) {
            throw new AssertionError("skip(15) should skip 15 but skipped " + skipped);
        }
        int next = bis.read();
        if (next != 15) {
            throw new AssertionError("Next byte after skip should be 15 but was " + next);
        }
        bis.close();
    }

    private static void testSkipWithMarkposNegative() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data));
        // markpos is initially -1 (default), so skip should delegate to underlying stream if buffer empty
        // First, read all bytes to empty buffer
        while (bis.read() != -1) {}
        // Now buffer is empty, markpos is -1
        long skipped = bis.skip(2);
        // Underlying stream is at EOF, so skip should return 0
        if (skipped != 0) {
            throw new AssertionError("skip on empty buffer with markpos -1 at EOF should return 0 but returned " + skipped);
        }
        bis.close();
    }
}