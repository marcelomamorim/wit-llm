/*
 * @test
 * @summary Test ProcessBuilder.write(int) throws IOException as expected
 * @run main ProcessBuilderWitupTest
 */

import java.io.IOException;
import java.lang.ProcessBuilder;

public class ProcessBuilderWitupTest {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder();
        try {
            pb.write(0);
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
