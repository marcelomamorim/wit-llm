/* @test    @summary Test ObjectOutputStream.reset() method for correct behavior and exception handling    @run main ObjectOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) {
        testResetNormalFlow();
        testResetActiveStream();
    }

    private static void testResetNormalFlow() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.reset();
            // If no exception, test passes for normal flow
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in normal reset", e);
        }
    }

    private static void testResetActiveStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            // Manually set depth to simulate active stream
            // Since 'depth' is package-private, we simulate by calling reset() twice
            oos.reset();
            // Second reset should throw IOException because stream is active
            oos.reset();
            throw new AssertionError("Expected IOException not thrown when resetting active stream");
        } catch (IOException e) {
            if (!e.getMessage().equals("stream active")) {
                throw new AssertionError("Unexpected IOException message: " + e.getMessage(), e);
            }
            // Expected exception, test passes
        }
    }
}
