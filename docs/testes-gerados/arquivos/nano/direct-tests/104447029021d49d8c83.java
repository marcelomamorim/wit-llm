/* @test    @summary Test OutputStream.write(int b) method for correct behavior and exception handling    @run main OutputStreamWitupTest */

import java.io.OutputStream;
import java.io.IOException;

public class OutputStreamWitupTest {

    public static void main(String[] args) {
        testWriteMethod();
    }

    private static void testWriteMethod() {
        OutputStream os = new OutputStream() {
            private boolean open = true;

            private void ensureOpen() throws IOException {
                if (!open) {
                    throw new IOException("Stream closed");
                }
            }

            @Override
            public void write(int b) throws IOException {
                ensureOpen();
            }

            @Override
            public void close() {
                open = false;
            }
        };

        try {
            os.write(65); // Should succeed
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException on write when open", e);
        }

        os.close();
        try {
            os.write(65); // Should throw IOException
            throw new AssertionError("Expected IOException not thrown after close");
        } catch (IOException e) {
            if (!"Stream closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
