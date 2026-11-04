/*
 * @test
 * @summary Verify that Process.info() propagates UnsupportedOperationException when toHandle() is not overridden
 * @run main ProcessWitupTest
 */
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessWitupTest {
    static final class StubProcess extends Process {
        public OutputStream getOutputStream() { return null; }
        public InputStream getInputStream() { return null; }
        public InputStream getErrorStream() { return null; }
        public int waitFor() throws InterruptedException { return 0; }
        public int exitValue() { return 0; }
        public void destroy() { }
    }

    public static void main(String[] args) throws Exception {
        Process p = new StubProcess();
        boolean thrown = false;
        try {
            p.info();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from Process.info()");
        }
    }
}
