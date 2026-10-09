/* @test    @summary Test Process.toHandle() method for UnsupportedOperationException.    @run main ProcessWitupTest_Pfafbaf81f984 */

import java.lang.Process;

public class ProcessWitupTest_Pfafbaf81f984 {
    public static void main(String[] args) {
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
            public ProcessHandle toHandle() {
                throw new UnsupportedOperationException(this.getClass() + ".toHandle() not supported");
            }
        };
        boolean exceptionThrown = false;
        try {
            process.toHandle();
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
