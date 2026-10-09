/* @test    @summary Test for Process.toHandle() method to verify exception behavior    @run main ProcessWitupTest_Pc6e59d41184a */

import java.lang.Process;

public class ProcessWitupTest_Pc6e59d41184a {

    public static void main(String[] args) {
        testToHandleThrowsException();
    }

    public static void testToHandleThrowsException() {
        Process process = new Process() {
            @Override
            public int waitFor() { return 0; }
            @Override
            public int exitValue() { return 0; }
            @Override
            public void destroy() {}
            @Override
            public boolean isAlive() { return false; }
            @Override
            public ProcessHandle toHandle() {
                throw new UnsupportedOperationException(this.getClass() + ".toHandle() not supported");
            }
        };
        try {
            process.toHandle();
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Caught expected exception: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
