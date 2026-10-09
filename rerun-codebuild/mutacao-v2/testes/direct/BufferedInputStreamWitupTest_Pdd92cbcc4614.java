/*
 * @test
 * @summary Test BufferedInputStream.reset() behavior
 * @run main BufferedInputStreamWitupTest_Pdd92cbcc4614
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest_Pdd92cbcc4614 {
    public static void main(String[] args) throws Exception {
        testResetWithoutMarkThrows();
        testResetAfterMarkResetsPosition();
    }

    private static void testResetWithoutMarkThrows() throws IOException {
        byte[] data = {1, 2, 3};
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data));
        // Initially markpos is -1, so reset() should throw IOException
        try {
            bis.reset();
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected
            if (!"Resetting to invalid mark".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
        bis.close();
    }

    private static void testResetAfterMarkResetsPosition() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data));

        // Mark the current position with readlimit 10
        bis.mark(10);

        // Read two bytes
        int first = bis.read();
        int second = bis.read();

        // Reset should set position back to mark
        bis.reset();

        // After reset, reading should return the first byte again
        int afterReset = bis.read();

        if (first != 10 || second != 20 || afterReset != 10) {
            throw new RuntimeException("Reset did not restore position correctly");
        }

        bis.close();
    }
}
