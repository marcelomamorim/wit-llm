/* @test    @summary Test for Writer.append(char c) method to verify behavior when stream is closed.    @run main WriterWitupTest */

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
            public Writer append(char c) throws IOException {
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
            writer.append('a');
            // If no exception, test fails
            System.err.println("FAIL: Expected IOException when appending to closed writer.");
        } catch (IOException e) {
            if ("Stream closed".equals(e.getMessage())) {
                System.out.println("PASS: IOException thrown as expected when appending to closed writer.");
            } else {
                System.err.println("FAIL: Unexpected IOException message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception type: " + e);
        }
    }
}
