/*
 * @test
 * @summary Test OutputStream nullOutputStream write(int) behavior
 * @run main OutputStreamWitupTest
 */

import java.io.OutputStream;
import java.io.IOException;

public class OutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        OutputStream os = OutputStream.nullOutputStream();

        // Test writing single bytes repeatedly while open
        for (int i = 0; i < 10; i++) {
            os.write(i);
        }

        // Close the stream
        os.close();

        // After close, write(int) should throw IOException
        boolean threw = false;
        try {
            os.write(0);
        } catch (IOException e) {
            threw = true;
            if (!"Stream closed".equals(e.getMessage())) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new AssertionError("Expected IOException after close() not thrown");
        }
    }
}
