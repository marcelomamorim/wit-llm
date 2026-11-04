/* @test    @summary Test setFixedLengthStreamingMode with valid and invalid inputs    @run main HttpURLConnectionWitupTest */

import java.net.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetFixedLengthStreamingModeValid();
        testSetFixedLengthStreamingModeConnected();
        testSetFixedLengthStreamingModeNegative();
    }

    private static void testSetFixedLengthStreamingModeValid() {
        HttpURLConnection connection = new HttpURLConnection(null) {
            @Override
            public void connect() throws java.io.IOException {}
        };
        try {
            connection.setFixedLengthStreamingMode(1024L);
            assert connection.fixedContentLengthLong == 1024L : "Content length not set correctly";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input", e);
        }
    }

    private static void testSetFixedLengthStreamingModeConnected() {
        HttpURLConnection connection = new HttpURLConnection(null) {
            boolean connected = true;
            @Override
            public void connect() throws java.io.IOException {
                this.connected = true;
            }
        };
        try {
            connection.connected = true;
            connection.setFixedLengthStreamingMode(512L);
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            assert e.getMessage().equals("Already connected") : "Unexpected exception message";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testSetFixedLengthStreamingModeNegative() {
        HttpURLConnection connection = new HttpURLConnection(null) {
            @Override
            public void connect() throws java.io.IOException {}
        };
        try {
            connection.setFixedLengthStreamingMode(-1L);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("invalid content length") : "Unexpected exception message";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
