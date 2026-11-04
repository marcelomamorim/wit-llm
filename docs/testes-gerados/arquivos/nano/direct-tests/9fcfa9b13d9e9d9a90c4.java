/*
 * @test
 * @summary Test the setDoOutput method of URLConnection.
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws IOException {
        testSetDoOutputTrue();
        testSetDoOutputFalse();
    }

    private static void testSetDoOutputTrue() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        assert connection.getDoOutput() == true : "Expected doOutput to be true";
    }

    private static void testSetDoOutputFalse() throws IOException {
        URL url = new URL("http://example.com");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(false);
        assert connection.getDoOutput() == false : "Expected doOutput to be false";
    }
}
