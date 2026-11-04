/*
 * @test
 * @summary Process.pid() delegates to toHandle(), which is unsupported by default in Process
 * @run main ProcessWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessWitupTest {
    public static void main(String[] args) throws Exception {
        Process p = new Process() {
            @Override
            public OutputStream getOutputStream() {
                return new ByteArrayOutputStream();
            }

            @Override
            public InputStream getInputStream() {
                return new ByteArrayInputStream(new byte[0]);
            }

            @Override
            public InputStream getErrorStream() {
                return new ByteArrayInputStream(new byte[0]);
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
            p.pid();
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected: default toHandle() is not supported by this Process
        }
    }
}
