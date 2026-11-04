/* @test    @summary Test for ProcessBuilder.write(int b) method to verify IOException is thrown when stream is closed    @run main ProcessBuilderWitupTest */

import java.io.IOException;
import java.lang.ProcessBuilder;

public class ProcessBuilderWitupTest {

    public static void main(String[] args) {
        testWriteThrowsIOException();
    }

    public static void testWriteThrowsIOException() {
        ProcessBuilder pb = new ProcessBuilder();
        try {
            pb.write(0);
            // If no exception, fail the test
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Verify that the exception message matches
            if (!"Stream closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
            // Test passes if IOException with correct message is thrown
        } catch (Throwable t) {
            // Fail if any other exception occurs
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
