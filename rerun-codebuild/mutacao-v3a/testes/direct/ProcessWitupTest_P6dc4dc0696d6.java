/*
 * @test
 * @summary Test supportsNormalTermination method of java.lang.Process
 * @run main ProcessWitupTest_P6dc4dc0696d6
 */

import java.lang.Process;

public class ProcessWitupTest_P6dc4dc0696d6 {

    public static void main(String[] args) {
        testSupportsNormalTermination();
    }

    public static void testSupportsNormalTermination() {
        Process process = new DummyProcess();
        boolean result = false;
        try {
            result = process.supportsNormalTermination();
        } catch (UnsupportedOperationException e) {
            result = false;
        }
        System.out.println("supportsNormalTermination: " + result);
    }

    static class DummyProcess extends Process {
        @Override
        public OutputStream getOutputStream() {
            return null;
        }

        @Override
        public InputStream getInputStream() {
            return null;
        }

        @Override
        public InputStream getErrorStream() {
            return null;
        }

        @Override
        public int waitFor() throws InterruptedException {
            return 0;
        }

        @Override
        public int exitValue() {
            return 0;
        }

        @Override
        public void destroy() {
        }

        @Override
        public boolean supportsNormalTermination() {
            return false;
        }
    }
}
