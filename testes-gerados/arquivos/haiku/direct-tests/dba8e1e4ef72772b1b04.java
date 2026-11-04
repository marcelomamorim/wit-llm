/*
 * @test
 * @summary Verify that the base Process.supportsNormalTermination() throws UnsupportedOperationException with a descriptive message
 * @run main ProcessWitupTest
 */
import java.io.*;

public class ProcessWitupTest {

    static class DummyProcess extends Process {
        public OutputStream getOutputStream() { return new ByteArrayOutputStream(); }
        public InputStream getInputStream() { return new ByteArrayInputStream(new byte[0]); }
        public InputStream getErrorStream() { return new ByteArrayInputStream(new byte[0]); }
        public int waitFor() throws InterruptedException { return 0; }
        public int exitValue() { return 0; }
        public void destroy() { }
    }

    public static void main(String[] args) throws Exception {
        Process p = new DummyProcess();
        boolean thrown = false;
        try {
            p.supportsNormalTermination();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("supportsNormalTermination() not supported")) {
                throw new RuntimeException("unexpected message: " + msg);
            }
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException");
        }
    }
}
