/*
 * @test
 * @summary Verify that the null output stream returned by Process.getOutputStream() when stdin is redirected from a file throws IOException on write(int)
 * @run main ProcessBuilderWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        File in = File.createTempFile("pbwitup", ".txt");
        try {
            String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            ProcessBuilder pb = new ProcessBuilder(java, "-version");
            pb.redirectInput(ProcessBuilder.Redirect.from(in));
            Process p = pb.start();
            OutputStream os = p.getOutputStream();
            try {
                os.write(1);
                throw new RuntimeException("Expected IOException from write(int) on null output stream");
            } catch (IOException e) {
                if (!"Stream closed".equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
                }
            }
            os.close();
            p.waitFor();
        } finally {
            in.delete();
        }
    }
}
