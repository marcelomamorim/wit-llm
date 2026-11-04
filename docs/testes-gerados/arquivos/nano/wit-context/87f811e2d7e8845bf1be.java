/* @test    @summary Test InputStream.read(byte[] b, int off, int len) for boundary conditions and exception paths    @run main InputStreamWitupTest */

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class InputStreamWitupTest {

    public static void main(String[] args) {
        testReadWithInvalidBounds();
        testReadWhenClosed();
    }

    private static void testReadWithInvalidBounds() {
        InputStream is = new InputStream() {
            private boolean closed = false;
            @Override
            public int read() throws IOException { return -1; }
            @Override
            public void close() throws IOException { closed = true; }
            private void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, b.length);
                ensureOpen();
                return -1;
            }
        };
        byte[] buffer = new byte[10];
        // Test negative offset
        try {
            is.read(buffer, -1, 5);
            System.err.println("Failed: Expected exception for negative offset");
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for negative offset");
        }
        // Test negative length
        try {
            is.read(buffer, 0, -1);
            System.err.println("Failed: Expected exception for negative length");
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for negative length");
        }
        // Test offset + length exceeding buffer length
        try {
            is.read(buffer, 8, 3);
            System.err.println("Failed: Expected exception for offset + length exceeding buffer");
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            // Expected
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException for offset + length exceeding buffer");
        }
    }

    private static void testReadWhenClosed() {
        InputStream is = new InputStream() {
            private boolean closed = false;
            @Override
            public int read() throws IOException { return -1; }
            @Override
            public void close() throws IOException { closed = true; }
            private void ensureOpen() throws IOException {
                if (closed) throw new IOException("Stream closed");
            }
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, b.length);
                ensureOpen();
                return -1;
            }
        };
        byte[] buffer = new byte[10];
        try {
            is.close();
            is.read(buffer, 0, 1);
            System.err.println("Failed: Expected IOException after close");
        } catch (IOException e) {
            // Expected
        }
    }
}
