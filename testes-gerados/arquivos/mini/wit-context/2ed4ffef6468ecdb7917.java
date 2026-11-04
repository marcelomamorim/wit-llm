/*
 * @test
 * @summary Test BufferedInputStream.skip(long) for correct behavior and exception handling
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testSkipWithClosedStream();
        testSkipWithNegativeOrZero();
        testSkipWithAvailableBytes();
        testSkipWithMarkposNegativeAndNoAvailable();
    }

    // Test that skip throws IOException if the stream is closed (buf == null)
    private static void testSkipWithClosedStream() {
        try {
            BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(new byte[10]));
            bis.close();
            bis.skip(1);
            throw new RuntimeException("Expected IOException not thrown when skipping on closed stream");
        } catch (IOException e) {
            // Expected
        }
    }

    // Test that skip returns 0 if n <= 0
    private static void testSkipWithNegativeOrZero() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(new byte[10]));
        long skippedZero = bis.skip(0);
        if (skippedZero != 0) {
            throw new RuntimeException("skip(0) should return 0 but returned " + skippedZero);
        }
        long skippedNegative = bis.skip(-5);
        if (skippedNegative != 0) {
            throw new RuntimeException("skip(-5) should return 0 but returned " + skippedNegative);
        }
        bis.close();
    }

    // Test that skip skips available bytes up to n
    private static void testSkipWithAvailableBytes() throws IOException {
        byte[] data = new byte[20];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data), 10);
        // Read some bytes to fill buffer partially
        bis.read(new byte[5]);
        // Now skip less than available
        long skipped = bis.skip(3);
        if (skipped != 3) {
            throw new RuntimeException("Expected to skip 3 bytes but skipped " + skipped);
        }
        // Skip more than available in buffer
        skipped = bis.skip(100);
        if (skipped <= 0) {
            throw new RuntimeException("Expected to skip some bytes but skipped " + skipped);
        }
        bis.close();
    }

    // Test that skip calls underlying stream skip if markpos < 0 and no available bytes
    private static void testSkipWithMarkposNegativeAndNoAvailable() throws IOException {
        byte[] data = new byte[5];
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data), 5);
        // Skip all bytes to exhaust buffer
        bis.skip(5);
        // Now buffer is empty, markpos is -1 by default
        // Skipping more should call underlying stream skip
        long skipped = bis.skip(1);
        if (skipped != 0) {
            // Underlying ByteArrayInputStream.skip returns 0 if no bytes left
            // So skipped should be 0
            throw new RuntimeException("Expected to skip 0 bytes but skipped " + skipped);
        }
        bis.close();
    }
}