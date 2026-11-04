/*
 * @test
 * @summary Test Process.supportsNormalTermination() throws UnsupportedOperationException
 * @run main ProcessWitupTest
 */

public class ProcessWitupTest {
    public static void main(String[] args) {
        Process p = new Process() {
            // Anonymous subclass to instantiate abstract Process
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

        boolean thrown = false;
        try {
            p.supportsNormalTermination();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("supportsNormalTermination")) {
                throw new AssertionError("Exception message does not mention supportsNormalTermination");
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
