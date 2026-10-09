/*
 * @test
 * @summary Test Process.toHandle() throws UnsupportedOperationException
 * @run main ProcessWitupTest_P5485f59cb3fc
 */

public class ProcessWitupTest_P5485f59cb3fc {
    public static void main(String[] args) {
        Process p = new Process() {
            @Override
            public OutputStream getOutputStream() { return null; }
            @Override
            public InputStream getInputStream() { return null; }
            @Override
            public InputStream getErrorStream() { return null; }
            @Override
            public int waitFor() { return 0; }
            @Override
            public int exitValue() { return 0; }
            @Override
            public void destroy() { }
        };
        try {
            p.toHandle();
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("toHandle() not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
