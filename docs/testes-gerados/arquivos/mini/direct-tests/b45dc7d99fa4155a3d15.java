/*
 * @test
 * @summary Test ProcessBuilder.write(int) throws IOException("Stream closed")
 * @run main ProcessBuilderWitupTest
 */

import java.io.IOException;
import java.lang.ProcessBuilder;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder();
        try {
            pb.write(0);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"Stream closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
