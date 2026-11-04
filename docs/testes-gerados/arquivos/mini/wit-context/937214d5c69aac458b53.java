/*
 * @test
 * @summary Test BufferedInputStream.available() behavior including IOException when underlying stream is closed
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAvailableReturnsCorrectValue();
        testAvailableThrowsIOExceptionWhenStreamClosed();
    }

    private static void testAvailableReturnsCorrectValue() throws IOException {
        byte[] data = new byte[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais, 5);

        // Initially, buffer is empty, so available() should be at least the underlying stream available
        int availableBeforeRead = bis.available();
        if (availableBeforeRead < 10) {
            throw new AssertionError("available() should be at least 10 but was " + availableBeforeRead);
        }

        // Read some bytes to fill buffer
        byte[] buf = new byte[3];
        int read = bis.read(buf);
        if (read != 3) {
            throw new AssertionError("Expected to read 3 bytes but read " + read);
        }

        // available() should reflect buffered bytes plus underlying stream available
        int availableAfterRead = bis.available();
        if (availableAfterRead < 7) {
            throw new AssertionError("available() after read should be at least 7 but was " + availableAfterRead);
        }

        // Read remaining bytes
        while (bis.read() != -1) {}

        int availableAtEnd = bis.available();
        if (availableAtEnd != 0) {
            throw new AssertionError("available() at end of stream should be 0 but was " + availableAtEnd);
        }

        bis.close();
    }

    private static void testAvailableThrowsIOExceptionWhenStreamClosed() {
        // Create a BufferedInputStream with a null underlying stream to simulate closed stream
        BufferedInputStream bis = new BufferedInputStream(new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Stream closed");
            }

            @Override
            public int available() throws IOException {
                throw new IOException("Stream closed");
            }
        });

        try {
            bis.close(); // Close the stream to nullify underlying stream
            bis.available();
            throw new AssertionError("Expected IOException when calling available() on closed stream");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
