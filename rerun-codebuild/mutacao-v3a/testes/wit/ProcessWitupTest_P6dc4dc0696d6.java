/* @test    @summary Test for supportsNormalTermination() method in java.lang.Process    @run main ProcessWitupTest_P6dc4dc0696d6 */

import java.lang.Process;

public class ProcessWitupTest_P6dc4dc0696d6 {

    public static void main(String[] args) {
        testSupportsNormalTerminationThrows();
    }

    public static void testSupportsNormalTerminationThrows() {
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
        };
        try {
            process.supportsNormalTermination();
            // Se não lançar exceção, falha:
            System.err.println("ERROR: Expected UnsupportedOperationException not thrown.");
        } catch (UnsupportedOperationException e) {
            // Esperado
            System.out.println("Caught expected exception: " + e.getMessage());
        } catch (Throwable t) {
            System.err.println("ERROR: Unexpected exception: " + t);
        }
    }
}
