/*
 * @test
 * @summary ProcessBuilder with redirected standard input returns a null OutputStream whose write(int) throws IOException("Stream closed") and whose close() does nothing
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        File in = File.createTempFile("pbwitup", ".txt");
        in.deleteOnExit();

        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        ProcessBuilder pb = new ProcessBuilder(java, "-version");
        pb.redirectInput(ProcessBuilder.Redirect.from(in));

        Process p = pb.start();
        try {
            OutputStream os = p.getOutputStream();
            try {
                os.write(1);
                throw new AssertionError("expected IOException from write(int)");
            } catch (IOException expected) {
                if (!"Stream closed".equals(expected.getMessage())) {
                    throw new AssertionError("unexpected message: " + expected.getMessage());
                }
            }
            os.close();
        } finally {
            p.waitFor();
        }
    }
}
