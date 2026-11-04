/* @test    @summary Test for Base64.available() method to verify exception handling and normal behavior    @run main Base64WitupTest */

import java.util.Base64;
import java.io.IOException;

public class Base64WitupTest {

    public static void main(String[] args) {
        testAvailableWhenStreamOpen();
        testAvailableWhenStreamClosed();
    }

    private static void testAvailableWhenStreamOpen() {
        Base64.DecInputStream stream = null;
        try {
            // Create a dummy InputStream with some data
            byte[] data = {1, 2, 3};
            stream = new Base64.DecInputStream(new java.io.ByteArrayInputStream(data));
            // Simulate stream not closed
            // Since 'closed' is package-private, assume stream is open
            int availableBytes = stream.available();
            if (availableBytes != data.length) {
                System.err.println("Test failed: available() returned unexpected value when stream is open.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Test failed: IOException thrown when stream is open: " + e);
            System.exit(1);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        System.out.println("testAvailableWhenStreamOpen passed.");
    }

    private static void testAvailableWhenStreamClosed() {
        Base64.DecInputStream stream = null;
        try {
            byte[] data = {1, 2, 3};
            stream = new Base64.DecInputStream(new java.io.ByteArrayInputStream(data));
            // Close the stream to simulate closed state
            stream.close();
            // Now, calling available() should throw IOException
            stream.available();
            System.err.println("Test failed: expected IOException when stream is closed.");
            System.exit(1);
        } catch (IOException e) {
            if (e.getMessage().equals("Stream is closed")) {
                System.out.println("testAvailableWhenStreamClosed passed.");
            } else {
                System.err.println("Test failed: IOException message mismatch: " + e);
                System.exit(1);
            }
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
