/*
 * @test
 * @summary Test the setUseCaches method of URLConnection.
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://example.com");
        URLConnection connection = url.openConnection();
        // Ensure connection is not connected yet
        try {
            connection.setUseCaches(true);
            assert connection != null : "Connection should not be null";
            // After setting, verify that the method completes without exception
            connection.setUseCaches(false);
        } catch (Exception e) {
            throw new AssertionError("setUseCaches threw an exception", e);
        }
    }
}
