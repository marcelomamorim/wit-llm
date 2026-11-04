/*
 * @test
 * @summary Test BufferedInputStream.available() method behavior
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testAvailableReturnsSumOfBufferAndStream();
        testAvailableReturnsMaxIntWhenSumExceedsMaxInt();
    }

    private static void testAvailableReturnsSumOfBufferAndStream() throws IOException {
        byte[] data = new byte[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais, 10);

        // Read 5 bytes to fill buffer partially
        byte[] readBuffer = new byte[5];
        int read = bis.read(readBuffer);
        if (read != 5) {
            throw new RuntimeException("Expected to read 5 bytes, but read " + read);
        }

        // available() should be (count - pos) + underlying.available()
        // count - pos = bytes buffered but not read = 10 - 5 = 5
        // underlying.available() = 5 (remaining bytes in ByteArrayInputStream)
        int available = bis.available();
        if (available != 10) {
            throw new RuntimeException("Expected available() to be 10, but was " + available);
        }
    }

    private static void testAvailableReturnsMaxIntWhenSumExceedsMaxInt() throws IOException {
        // Create a custom InputStream that returns Integer.MAX_VALUE on available()
        java.io.InputStream customStream = new java.io.InputStream() {
            @Override
            public int read() throws IOException {
                return -1; // EOF
            }

            @Override
            public int available() throws IOException {
                return Integer.MAX_VALUE;
            }
        };

        BufferedInputStream bis = new BufferedInputStream(customStream, 10);

        // Manually set buf and count to simulate buffered data
        // Use reflection since buf and count are protected
        try {
            java.lang.reflect.Field bufField = BufferedInputStream.class.getDeclaredField("buf");
            java.lang.reflect.Field countField = BufferedInputStream.class.getDeclaredField("count");
            java.lang.reflect.Field posField = BufferedInputStream.class.getDeclaredField("pos");
            bufField.setAccessible(true);
            countField.setAccessible(true);
            posField.setAccessible(true);

            bufField.set(bis, new byte[10]);
            countField.setInt(bis, 10);
            posField.setInt(bis, 0);

            int available = bis.available();
            if (available != Integer.MAX_VALUE) {
                throw new RuntimeException("Expected available() to be Integer.MAX_VALUE, but was " + available);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed", e);
        }
    }
}
