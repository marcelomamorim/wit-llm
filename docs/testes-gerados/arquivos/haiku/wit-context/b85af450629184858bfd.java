/*
 * @test
 * @summary Process.children() must propagate UnsupportedOperationException when the default toHandle() is not overridden
 * @run main ProcessWitupTest
 */

import java.io.InputStream;
import java.io.OutputStream;

public class ProcessWitupTest {

    static class MinimalProcess extends Process {
        public OutputStream getOutputStream() { return null; }
        public InputStream getInputStream() { return null; }
        public InputStream getErrorStream() { return null; }
        public int waitFor() throws InterruptedException { return 0; }
        public int exitValue() { return 0; }
        public void destroy() { }
    }

    public static void main(String[] args) {
        Process p = new MinimalProcess();
        try {
            p.children();
            throw new RuntimeException("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
