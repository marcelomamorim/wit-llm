/*
 * @test
 * @summary Test Process.toHandle() throws UnsupportedOperationException
 * @run main ProcessWitupTest_P5485f59cb3fc
 */

public class ProcessWitupTest_P5485f59cb3fc {
    public static void main(String[] args) {
        Process p = new Process() {
            // Anonymous subclass to instantiate abstract Process
            @Override
            public int waitFor() { return 0; }
            @Override
            public int exitValue() { return 0; }
            @Override
            public void destroy() { }
            @Override
            public java.io.OutputStream getOutputStream() { return null; }
            @Override
            public java.io.InputStream getInputStream() { return null; }
            @Override
            public java.io.InputStream getErrorStream() { return null; }
        };

        boolean thrown = false;
        try {
            p.toHandle();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("toHandle() not supported")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
        if (!thrown) {
            throw new AssertionError("UnsupportedOperationException was not thrown by toHandle()");
        }
    }
}
