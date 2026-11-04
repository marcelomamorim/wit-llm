/* @test    @summary Test for OutputStream.write(int b) method, focusing on exception path when stream is closed.    @run main OutputStreamWitupTest */

import java.io.OutputStream;
import java.io.IOException;

public class OutputStreamWitupTest {

    public static void main(String[] args) {
        testWriteThrowsWhenClosed();
    }

    private static void testWriteThrowsWhenClosed() {
        OutputStream os = new OutputStream() {
            private boolean closed = false;

            private void ensureOpen() throws IOException {
                if (closed) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public void write(int b) throws IOException {
                ensureOpen();
            }

            @Override
            public void close() {
                closed = true;
            }
        };
        os.close();
        try {
            os.write(1);
            System.err.println("FAIL: Expected IOException not thrown");
        } catch (IOException e) {
            if ("Stream closed".equals(e.getMessage())) {
                System.out.println("PASS: IOException thrown as expected when writing to closed stream");
            } else {
                System.err.println("FAIL: Unexpected IOException message: " + e.getMessage());
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
