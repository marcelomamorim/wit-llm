/*
 * @test
 * @summary Test BufferedInputStream.read() for expected IOException and normal behavior
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadReturnsByte();
        testReadReturnsMinusOneAtEOF();
        testReadThrowsIOExceptionWhenClosedBuffer();
        testReadThrowsIOExceptionWhenClosedStream();
    }

    private static void testReadReturnsByte() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);

        int first = bis.read();
        if (first != 10) {
            throw new AssertionError("Expected first byte 10, got " + first);
        }

        int second = bis.read();
        if (second != 20) {
            throw new AssertionError("Expected second byte 20, got " + second);
        }

        int third = bis.read();
        if (third != 30) {
            throw new AssertionError("Expected third byte 30, got " + third);
        }
    }

    private static void testReadReturnsMinusOneAtEOF() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);

        int result = bis.read();
        if (result != -1) {
            throw new AssertionError("Expected -1 at EOF, got " + result);
        }
    }

    private static void testReadThrowsIOExceptionWhenClosedBuffer() {
        // This test tries to provoke IOException by closing the stream and then reading
        // According to the source, if buf == null, read() throws IOException
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);

        try {
            bis.close();
            bis.read();
            throw new AssertionError("Expected IOException after close() when reading");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testReadThrowsIOExceptionWhenClosedStream() {
        // This test tries to provoke IOException by closing the underlying stream and then reading
        // We simulate this by creating a custom InputStream that throws IOException on read
        java.io.InputStream failingStream = new java.io.InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Stream closed");
            }
        };

        BufferedInputStream bis = new BufferedInputStream(failingStream);

        try {
            bis.read();
            throw new AssertionError("Expected IOException from underlying stream");
        } catch (IOException e) {
            // Expected
        }
    }
}
