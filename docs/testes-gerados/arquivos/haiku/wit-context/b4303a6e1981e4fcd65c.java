/*
 * @test
 * @summary Verify that the default Process.toHandle() throws UnsupportedOperationException with the documented message
 * @run main ProcessWitupTest
 */

public class ProcessWitupTest {

    static class StubProcess extends Process {
        public java.io.OutputStream getOutputStream() { return null; }
        public java.io.InputStream getInputStream() { return null; }
        public java.io.InputStream getErrorStream() { return null; }
        public int waitFor() throws InterruptedException { return 0; }
        public int exitValue() { return 0; }
        public void destroy() { }
    }

    public static void main(String[] args) throws Exception {
        Process p = new StubProcess();
        boolean thrown = false;
        String msg = null;
        try {
            p.toHandle();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            msg = e.getMessage();
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException from toHandle()");
        }
        if (msg == null || !msg.contains("toHandle() not supported")) {
            throw new RuntimeException("unexpected message: " + msg);
        }
    }
}
