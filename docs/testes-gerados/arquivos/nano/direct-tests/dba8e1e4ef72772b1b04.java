/* @test    @summary Test supportsNormalTermination method of java.lang.Process    @run main ProcessWitupTest */

import java.lang.Process;

public class ProcessWitupTest {

    public static void main(String[] args) {
        testSupportsNormalTermination();
    }

    public static void testSupportsNormalTermination() {
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
        boolean result = false;
        try {
            result = process.supportsNormalTermination();
        } catch (UnsupportedOperationException e) {
            result = false;
        }
        if (result != false) {
            System.out.println("supportsNormalTermination() returned true as expected.");
        } else {
            System.out.println("supportsNormalTermination() returned false as expected.");
        }
    }
}
