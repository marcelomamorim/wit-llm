/* @test    @summary Test ProcessBuilder.write method for exception throwing behavior    @run main ProcessBuilderWitupTest */

import java.lang.ProcessBuilder;
import java.io.IOException;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testWriteThrowsIOException();
    }

    public static void testWriteThrowsIOException() {
        ProcessBuilder pb = new ProcessBuilder();
        try {
            pb.write(0);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Verify that the exception message matches
            if (!"Stream closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
