/*
 * @test
 * @summary Test Process.toHandle() throws UnsupportedOperationException
 * @run main ProcessWitupTest_Pf9bf38925c4a
 */

import java.lang.Process;
import java.lang.ProcessHandle;

public class ProcessWitupTest_Pf9bf38925c4a {
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
                try {
                    process.toHandle();
                } catch (UnsupportedOperationException e) {
                    System.out.println("Caught expected UnsupportedOperationException");
                    return null;
                }
                return null;
            }
        };
        try {
            process.toHandle();
            System.out.println("Error: toHandle() did not throw exception");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: toHandle() threw UnsupportedOperationException as expected");
        }
    }
}
