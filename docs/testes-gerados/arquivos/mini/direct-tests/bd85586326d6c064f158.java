/*
 * @test
 * @summary Test java.net.URLConnection.setDoInput(boolean) behavior
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {

    // Subclass to expose protected constructor and allow testing
    static class TestURLConnection extends URLConnection {

        protected TestURLConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
            connected = true;
        }

        // Expose doInput for testing
        public boolean getDoInput() {
            return doInput;
        }

        // Expose checkConnected to test exception
        public void callSetDoInput(boolean val) throws IOException {
            setDoInput(val);
        }
    }

    public static void main(String[] args) throws Exception {
        // Create a dummy URL
        URL url = new URL("http://example.com");
        TestURLConnection conn = new TestURLConnection(url);

        // Test that calling setDoInput before connect throws IOException
        try {
            conn.callSetDoInput(true);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected
        }

        // Connect and then test setDoInput
        conn.connect();

        // Initially doInput is true by default in URLConnection
        // Set to false and verify
        conn.setDoInput(false);
        if (conn.getDoInput() != false) {
            throw new RuntimeException("setDoInput(false) did not update doInput field");
        }

        // Set to true and verify
        conn.setDoInput(true);
        if (conn.getDoInput() != true) {
            throw new RuntimeException("setDoInput(true) did not update doInput field");
        }
    }
}
