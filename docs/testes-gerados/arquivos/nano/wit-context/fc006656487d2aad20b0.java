/* @test    @summary Test for Writer.append(CharSequence) method to verify behavior when stream is closed    @run main WriterWitupTest */

import java.io.Writer;
import java.io.IOException;

public class WriterWitupTest {

    public static void main(String[] args) {
        testAppendWhenClosed();
    }

    private static void testAppendWhenClosed() {
        Writer writer = new Writer() {
            private boolean closed = false;

            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public Writer append(CharSequence csq) throws IOException {
                ensureOpen();
                return this;
            }

            @Override
            public void close() throws IOException {
                closed = true;
            }
        };
        try {
            writer.close();
            writer.append("test");
            // If no exception, test fails
            System.err.println("Expected IOException was not thrown.");
        } catch (IOException e) {
            if ("Stream closed".equals(e.getMessage())) {
                System.out.println("Test passed: IOException thrown as expected.");
            } else {
                System.err.println("Test failed: Unexpected exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Test failed: Unexpected exception type: " + e);
        }
    }
}
