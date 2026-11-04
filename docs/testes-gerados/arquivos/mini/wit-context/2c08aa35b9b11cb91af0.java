/*
 * @test
 * @summary Test java.io.OutputStream.write(byte[]) method for correct delegation and bounds handling
 * @run main OutputStreamWitupTest
 */

import java.io.OutputStream;
import java.io.IOException;

public class OutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteDelegatesToWriteWithOffsetAndLength();
        testWriteWithNullArrayThrowsNullPointerException();
        testWriteWithEmptyArray();
    }

    private static void testWriteDelegatesToWriteWithOffsetAndLength() throws IOException {
        final boolean[] called = new boolean[1];
        OutputStream os = new OutputStream() {
            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                called[0] = true;
                if (off != 0) throw new IOException("Offset must be 0");
                if (len != b.length) throw new IOException("Length must be b.length");
            }

            @Override
            public void write(int b) throws IOException {
                // no-op
            }
        };
        byte[] data = new byte[] {1, 2, 3};
        os.write(data);
        if (!called[0]) {
            throw new AssertionError("write(byte[]) did not delegate to write(byte[], int, int)");
        }
    }

    private static void testWriteWithNullArrayThrowsNullPointerException() {
        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // no-op
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                // no-op
            }
        };
        try {
            os.write(null);
            throw new AssertionError("Expected NullPointerException when writing null array");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException instead of NullPointerException", e);
        }
    }

    private static void testWriteWithEmptyArray() throws IOException {
        OutputStream os = new OutputStream() {
            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                if (len != 0) {
                    throw new IOException("Length must be zero for empty array");
                }
            }

            @Override
            public void write(int b) throws IOException {
                // no-op
            }
        };
        byte[] empty = new byte[0];
        os.write(empty);
    }
}
