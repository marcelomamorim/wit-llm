/* @test    @summary Test for HttpURLConnection.setRequestMethod to verify behavior when connected.    @run main HttpURLConnectionWitupTest */

import java.net.HttpURLConnection;
import java.net.ProtocolException;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetRequestMethodWhenConnected();
        testSetRequestMethodWithValidMethod();
        testSetRequestMethodWithInvalidMethod();
    }

    private static void testSetRequestMethodWhenConnected() {
        HttpURLConnection connection = new HttpURLConnection() {
            private boolean connected = true;
            @Override
            public void connect() { this.connected = true; }
            @Override
            public boolean usingProxy() { return false; }
        };
        try {
            connection.setRequestMethod("GET");
            throw new AssertionError("Expected ProtocolException was not thrown");
        } catch (ProtocolException e) {
            // Expected exception
            if (!"Can't reset method: already connected".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testSetRequestMethodWithValidMethod() throws ProtocolException {
        HttpURLConnection connection = new HttpURLConnection() {
            private boolean connected = false;
            @Override
            public void connect() { this.connected = true; }
            @Override
            public boolean usingProxy() { return false; }
        };
        // Should set method without exception
        connection.setRequestMethod("POST");
        // No assertion needed, test passes if no exception
    }

    private static void testSetRequestMethodWithInvalidMethod() throws ProtocolException {
        HttpURLConnection connection = new HttpURLConnection() {
            private boolean connected = false;
            @Override
            public void connect() { this.connected = true; }
            @Override
            public boolean usingProxy() { return false; }
        };
        try {
            connection.setRequestMethod("INVALID_METHOD");
            throw new AssertionError("Expected ProtocolException was not thrown");
        } catch (ProtocolException e) {
            // Expected exception
            if (!"Invalid HTTP method: INVALID_METHOD".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
