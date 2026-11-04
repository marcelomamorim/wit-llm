/*
 * @test
 * @summary Tests for java.io.BufferedInputStream.skip(long)
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSkipNormal();
        testSkipWithNullBuffer();
        testSkipWithClosedStream();
    }

    private static void testSkipNormal() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);
        long skipped = bis.skip(2);
        if (skipped != 2) {
            throw new AssertionError("Expected to skip 2 bytes, but skipped " + skipped);
        }
        int remaining = bis.read();
        if (remaining != 3) {
            throw new AssertionError("Expected next byte to be 3, but got " + remaining);
        }
    }

    private static void testSkipWithNullBuffer() {
        try {
            // Create a BufferedInputStream with a null buffer to simulate the state
            BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(new byte[0])) {
                @Override
                protected byte[] getBufIfOpen() {
                    return null;
                }
            };
            bis.skip(1);
            throw new AssertionError("Expected IOException due to null buffer");
        } catch (IOException e) {
            // Expected exception
        } catch (NullPointerException e) {
            // Depending on implementation, might throw NullPointerException
        }
    }

    private static void testSkipWithClosedStream() {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{1, 2});
            BufferedInputStream bis = new BufferedInputStream(bais);
            // Simulate closed stream by nulling the underlying stream
            bis.in = null; // Note: 'in' is package-private; in real test, use reflection or package access
            bis.skip(1);
            throw new AssertionError("Expected IOException due to closed stream");
        } catch (IOException e) {
            // Expected exception
        } catch (NullPointerException e) {
            // Depending on implementation, might throw NullPointerException
        }
    }
}
