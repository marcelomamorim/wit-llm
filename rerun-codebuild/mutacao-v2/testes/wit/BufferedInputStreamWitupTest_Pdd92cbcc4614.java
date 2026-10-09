/*
 * @test
 * @summary Test BufferedInputStream.reset() for expected IOException on closed stream and invalid mark
 * @run main BufferedInputStreamWitupTest_Pdd92cbcc4614
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest_Pdd92cbcc4614 {
    public static void main(String[] args) throws Exception {
        testResetThrowsWhenClosed();
        testResetThrowsWhenMarkInvalid();
        testResetSucceedsWhenMarkValid();
    }

    private static void testResetThrowsWhenClosed() {
        byte[] data = {1, 2, 3};
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data));
        try {
            bis.close();
            bis.reset();
            throw new RuntimeException("Expected IOException not thrown when resetting closed stream");
        } catch (IOException e) {
            // Expected IOException because stream is closed (buf == null)
        }
    }

    private static void testResetThrowsWhenMarkInvalid() throws IOException {
        byte[] data = {1, 2, 3};
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data));
        // markpos is initially -1 (invalid)
        try {
            bis.reset();
            throw new RuntimeException("Expected IOException not thrown when resetting with invalid markpos");
        } catch (IOException e) {
            // Expected IOException because markpos < 0
        }
    }

    private static void testResetSucceedsWhenMarkValid() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data));
        bis.mark(10); // markpos set to 0
        int firstByte = bis.read(); // read one byte, pos increments
        bis.reset(); // should succeed, pos reset to markpos
        int resetByte = bis.read();
        if (resetByte != firstByte) {
            throw new RuntimeException("Reset did not reposition to mark correctly");
        }
    }
}
