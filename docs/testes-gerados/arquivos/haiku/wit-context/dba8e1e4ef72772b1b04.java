/*
 * @test
 * @summary Verifies that the base Process.supportsNormalTermination() throws UnsupportedOperationException
 * @run main ProcessWitupTest
 */

import java.io.InputStream;
import java.io.OutputStream;

public class ProcessWitupTest {
    public static void main(String[] args) throws Exception {
        Process p = new Process() {
            @Override
            public OutputStream getOutputStream() {
                return null;
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public InputStream getErrorStream() {
                return null;
            }

            @Override
            public int waitFor() throws InterruptedException {
                return 0;
            }

            @Override
            public int exitValue() {
                return 0;
            }

            @Override
            public void destroy() {
            }
        };

        try {
            p.supportsNormalTermination();
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("supportsNormalTermination() not supported")) {
                throw new RuntimeException("Unexpected message: " + msg);
            }
            return;
        }
        throw new RuntimeException("UnsupportedOperationException was not thrown");
    }
}
