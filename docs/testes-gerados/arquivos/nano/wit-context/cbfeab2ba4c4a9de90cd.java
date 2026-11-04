/* @test    @summary Test transferTo method for java.io.Reader    @run main ReaderWitupTest */

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;

public class ReaderWitupTest {

    public static void main(String[] args) {
        testTransferToNullOut();
        testTransferToClosedReader();
    }

    private static void testTransferToNullOut() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public int read() throws IOException {
                ensureOpen();
                return -1;
            }
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return -1;
            }
            @Override
            public long transferTo(Writer out) throws IOException {
                Objects.requireNonNull(out);
                ensureOpen();
                return 0L;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            reader.transferTo(null);
            System.out.println("testTransferToNullOut: Failed - no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testTransferToNullOut: Passed");
        } catch (IOException e) {
            System.out.println("testTransferToNullOut: Failed - unexpected IOException");
        }
    }

    private static void testTransferToClosedReader() {
        Reader reader = new Reader() {
            private boolean closed = false;
            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }
            @Override
            public int read() throws IOException {
                ensureOpen();
                return -1;
            }
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return -1;
            }
            @Override
            public long transferTo(Writer out) throws IOException {
                Objects.requireNonNull(out);
                ensureOpen();
                return 0L;
            }
            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            reader.close();
            reader.transferTo(new java.io.StringWriter());
            System.out.println("testTransferToClosedReader: Failed - no exception thrown");
        } catch (IOException e) {
            if (e.getMessage().equals("Stream closed")) {
                System.out.println("testTransferToClosedReader: Passed");
            } else {
                System.out.println("testTransferToClosedReader: Failed - unexpected message");
            }
        }
    }
}
