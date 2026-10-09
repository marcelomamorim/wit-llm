/* @test    @summary Test for supportsNormalTermination method in java.lang.Process    @run main ProcessWitupTest_P4708aa30e093 */

import java.lang.Process;

public class ProcessWitupTest_P4708aa30e093 {

    public static void main(String[] args) {
        testSupportsNormalTerminationThrows();
        testSupportsNormalTerminationReturnsDefault();
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
            @Override
            public boolean supportsNormalTermination() {
                throw new UnsupportedOperationException("Test exception") ;
            }
        };
        boolean threw = false;
        try {
            process.supportsNormalTermination();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException");
        }
    }

    private static void testSupportsNormalTerminationReturnsDefault() {
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
            @Override
            public boolean supportsNormalTermination() {
                return false;
            }
        };
        boolean result = process.supportsNormalTermination();
        if (result != false) {
            throw new AssertionError("Expected false");
        }
    }
}
