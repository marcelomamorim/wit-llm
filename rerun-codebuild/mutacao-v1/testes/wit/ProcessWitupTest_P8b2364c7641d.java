/* @test    @summary Test for supportsNormalTermination method in java.lang.Process    @run main ProcessWitupTest_P8b2364c7641d */

import java.lang.Process;

public class ProcessWitupTest_P8b2364c7641d {

    public static void main(String[] args) {
        testSupportsNormalTerminationThrows();
    }

    private static void testSupportsNormalTerminationThrows() {
        Process process = new Process() {
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
            boolean result = process.supportsNormalTermination();
            throw new AssertionError("Expected UnsupportedOperationException, but got result: " + result);
        } catch (UnsupportedOperationException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("not supported")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
        } catch (Throwable t) {
            throw new AssertionError("Expected UnsupportedOperationException, but got: " + t, t);
        }
    }
}
