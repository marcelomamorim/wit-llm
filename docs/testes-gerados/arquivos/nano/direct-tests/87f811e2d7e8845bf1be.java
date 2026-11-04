/*
 * @test
 * @summary Test InputStream.read(byte[] b, int off, int len) method for correct behavior.
 * @run main InputStreamWitupTest
 */

import java.io.InputStream;
import java.io.IOException;
import java.util.Arrays;

public class InputStreamWitupTest {

    public static void main(String[] args) {
        testReadWithZeroLength();
        testReadWithNullBuffer();
        testReadWhenStreamClosed();
        testReadReturnsMinusOneAtEnd();
    }

    static void testReadWithZeroLength() {
        InputStream stream = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return super.read(b, off, len);
            }
        };
        byte[] buffer = new byte[10];
        try {
            int result = stream.read(buffer, 0, 0);
            if (result != 0) {
                throw new AssertionError("Expected 0, got " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    static void testReadWithNullBuffer() {
        InputStream stream = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return super.read(b, off, len);
            }
        };
        try {
            stream.read(null, 0, 1);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    static void testReadWhenStreamClosed() {
        InputStream stream = new InputStream() {
            private boolean closed = false;
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
                return -1;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            stream.close();
            stream.read(new byte[1], 0, 1);
            throw new AssertionError("Expected IOException after close");
        } catch (IOException e) {
            // expected
        }
    }

    static void testReadReturnsMinusOneAtEnd() {
        InputStream stream = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return -1;
            }
        };
        byte[] buffer = new byte[10];
        try {
            int result = stream.read(buffer, 0, buffer.length);
            if (result != -1) {
                throw new AssertionError("Expected -1, got " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
