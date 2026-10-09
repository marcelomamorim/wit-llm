/* @test
   @summary Verifies the behavior of Process.toHandle() method.
   @run main ProcessWitupTest_Pe380cfdeaef8
 */
import java.lang.Process;
public class ProcessWitupTest_Pe380cfdeaef8 {
    public static void main(String[] args) {
        Process process = new Process() {
            @Override
            public int waitFor() throws InterruptedException { return 0; }
            @Override
            public int exitValue() { return 0; }
            @Override
            public void destroy() {}
            @Override
            public ProcessHandle toHandle() {
                throw new UnsupportedOperationException(this.getClass() + ".toHandle() not supported");
            }
            @Override
            public java.io.OutputStream getOutputStream() { return null; }
            @Override
            public java.io.InputStream getInputStream() { return null; }
            @Override
            public java.io.InputStream getErrorStream() { return null; }
        };
        try {
            process.toHandle();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // expected exception
        }
    }
}
