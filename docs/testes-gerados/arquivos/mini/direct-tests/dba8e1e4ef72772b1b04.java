/*
 * @test
 * @summary Test Process.supportsNormalTermination() throws UnsupportedOperationException
 * @run main ProcessWitupTest
 */

public class ProcessWitupTest {
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
            public void destroy() {}
        };

        try {
            p.supportsNormalTermination();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("supportsNormalTermination")) {
                throw new RuntimeException("Exception message does not contain method name");
            }
        }
    }
}
