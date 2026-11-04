/* @test    @summary Test for java.io.Writer.write(int c) to verify IOException when stream is closed    @run main WriterWitupTest */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {

    public static void main(String[] args) {
        testWriteThrowsWhenClosed();
    }

    private static void testWriteThrowsWhenClosed() {
        Writer writer = new Writer() {
            private boolean isClosed = false;

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                if (isClosed) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public void flush() throws IOException {
                if (isClosed) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public void close() throws IOException {
                isClosed = true;
            }

            @Override
            public void write(int c) throws IOException {
                if (isClosed) {
                    throw new IOException("Stream closed");
                }
            }
        };
        try {
            writer.close();
            writer.write(65); // ASCII 'A'
            System.err.println("FAIL: Expected IOException was not thrown.");
        } catch (IOException e) {
            if ("Stream closed".equals(e.getMessage())) {
                System.out.println("PASS: IOException thrown as expected.");
            } else {
                System.err.println("FAIL: Unexpected IOException message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception type: " + e);
        }
    }
}
