/*
 * @test
 * @summary Test InputStream.read(byte[], int, int) method behavior
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class InputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadWithValidParameters();
        testReadWithZeroLength();
        testReadWithInvalidParameters();
    }

    private static void testReadWithValidParameters() throws IOException {
        InputStream is = new InputStream() {
            private boolean open = true;
            private void ensureOpen() throws IOException {
                if (!open) throw new IOException("Stream closed");
            }
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, b.length);
                if (len == 0) {
                    return 0;
                }
                ensureOpen();
                return -1;
            }
        };

        byte[] buffer = new byte[10];
        int result = is.read(buffer, 2, 5);
        if (result != -1) {
            throw new AssertionError("Expected -1 but got " + result);
        }
    }

    private static void testReadWithZeroLength() throws IOException {
        InputStream is = new InputStream() {
            private boolean open = true;
            private void ensureOpen() throws IOException {
                if (!open) throw new IOException("Stream closed");
            }
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, b.length);
                if (len == 0) {
                    return 0;
                }
                ensureOpen();
                return -1;
            }
        };

        byte[] buffer = new byte[10];
        int result = is.read(buffer, 0, 0);
        if (result != 0) {
            throw new AssertionError("Expected 0 but got " + result);
        }
    }

    private static void testReadWithInvalidParameters() {
        InputStream is = new InputStream() {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, b.length);
                if (len == 0) {
                    return 0;
                }
                return -1;
            }
        };

        byte[] buffer = new byte[10];

        // off < 0
        try {
            is.read(buffer, -1, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for off < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // len < 0
        try {
            is.read(buffer, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for len < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // off + len > buffer.length
        try {
            is.read(buffer, 8, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for off + len > buffer.length");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
