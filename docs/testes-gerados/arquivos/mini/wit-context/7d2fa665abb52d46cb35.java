/*
 * @test
 * @summary Test InputStream.read(byte[]) method for argument validation and delegation
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadNullArray();
        testReadEmptyArray();
        testReadDelegation();
    }

    // Test that read(byte[]) throws NullPointerException when passed null
    private static void testReadNullArray() {
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return -1;
            }
        };

        try {
            is.read(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    // Test that read(byte[]) with zero-length array returns 0 without error
    private static void testReadEmptyArray() throws IOException {
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                if (len == 0) return 0;
                return -1;
            }
        };

        byte[] empty = new byte[0];
        int result = is.read(empty);
        if (result != 0) {
            throw new RuntimeException("Expected 0 for zero-length array, got " + result);
        }
    }

    // Test that read(byte[]) delegates correctly to read(byte[], int, int)
    private static void testReadDelegation() throws IOException {
        final byte[] buffer = new byte[10];
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                if (b != buffer || off != 0 || len != buffer.length) {
                    throw new RuntimeException("read(byte[], int, int) called with unexpected arguments");
                }
                // Simulate reading 5 bytes
                for (int i = off; i < off + len; i++) {
                    b[i] = (byte) i;
                }
                return 5;
            }
        };

        int readCount = is.read(buffer);
        if (readCount != 5) {
            throw new RuntimeException("Expected 5 bytes read, got " + readCount);
        }
        for (int i = 0; i < 5; i++) {
            if (buffer[i] != (byte) i) {
                throw new RuntimeException("Buffer content mismatch at index " + i);
            }
        }
    }
}
