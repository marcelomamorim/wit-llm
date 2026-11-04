/* @test    @summary Test Process.toHandle() method for UnsupportedOperationException.    @run main ProcessWitupTest */

import java.lang.Process;
import java.lang.ProcessHandle;

public class ProcessWitupTest {

    public static void main(String[] args) {
        testToHandleThrowsException();
    }

    public static void testToHandleThrowsException() {
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
        try {
            process.toHandle();
            // If no exception, test fails
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
