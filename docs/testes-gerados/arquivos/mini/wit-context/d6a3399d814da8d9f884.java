/*
 * @test
 * @summary Deterministic tests for java.net.URL.openConnection()
 * @run main URLWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLWitupTest {
    public static void main(String[] args) throws Exception {
        testOpenConnectionValidURL();
    }

    private static void testOpenConnectionValidURL() throws IOException {
        // Create a valid URL and open connection
        URL url = new URL("http://www.example.com");
        URLConnection conn = url.openConnection();
        if (conn == null) {
            throw new AssertionError("openConnection() returned null");
        }
    }
}
