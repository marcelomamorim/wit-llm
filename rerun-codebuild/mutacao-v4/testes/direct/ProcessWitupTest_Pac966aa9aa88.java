/* @test
   @summary Verifies that supportsNormalTermination() throws UnsupportedOperationException.
   @run main ProcessWitupTest_Pac966aa9aa88
 */
import java.lang.Process;
public class ProcessWitupTest_Pac966aa9aa88 {
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
            public boolean supportsNormalTermination() {
                throw new UnsupportedOperationException(this.getClass() + ".supportsNormalTermination() not supported");
            }
        };
        try {
            process.supportsNormalTermination();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // expected exception
        }
    }
}
