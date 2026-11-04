/* @test    @summary Test for Process.info() method to verify behavior and exception handling    @run main ProcessWitupTest */

import java.lang.Process;
import java.lang.ProcessHandle;

public class ProcessWitupTest {

    public static void main(String[] args) {
        testProcessInfoThrowsException();
    }

    private static void testProcessInfoThrowsException() {
        Process process = new Process() {
            @Override
            public ProcessHandle toHandle() {
                return new ProcessHandle() {
                    @Override
                    public Info info() {
                        throw new UnsupportedOperationException();
                    }
                    // Implement other methods as needed with minimal stub or default behavior
                    @Override public long pid() { return 0; }
                    @Override public boolean isAlive() { return false; }
                    @Override public void destroy() {}
                    @Override public boolean destroyForcibly() { return false; }
                    @Override public boolean supportsNormalTermination() { return false; }
                };
            }
            @Override
            public int waitFor() { return 0; }
            @Override
            public int exitValue() { return 0; }
            @Override
            public void destroy() {}
        };
        try {
            process.info();
            // If no exception, test fails
            System.err.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Test passed: UnsupportedOperationException was thrown as expected.");
        } catch (Exception e) {
            System.err.println("Test failed: Unexpected exception: " + e);
        }
    }
}
