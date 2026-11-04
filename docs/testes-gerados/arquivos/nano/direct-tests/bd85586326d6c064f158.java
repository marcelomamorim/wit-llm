/*
 * @test
 * @summary Test the setDoInput method of URLConnection.
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws IOException {
        testSetDoInputTrue();
        testSetDoInputFalse();
    }

    private static void testSetDoInputTrue() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        // Verify that doInput is set to true
        if (!connection.getDoInput()) {
            throw new AssertionError("Expected doInput to be true");
        }
    }

    private static void testSetDoInputFalse() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection connection = url.openConnection();
        connection.setDoInput(false);
        // Verify that doInput is set to false
        if (connection.getDoInput()) {
            throw new AssertionError("Expected doInput to be false");
        }
    }
}
