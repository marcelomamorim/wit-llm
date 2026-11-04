/*
 * @test
 * @summary Test for java.net.URLConnection.getRequestProperties() method.
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetRequestPropertiesConnected();
        testGetRequestPropertiesNotConnected();
    }

    private static void testGetRequestPropertiesConnected() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Simulate connection established
        conn.connect();
        // Force internal state to connected (via reflection or mock if needed)
        // Since connect() is protected, assume connection is established
        // and getRequestProperties() can be called
        Map<String, List<String>> props = conn.getRequestProperties();
        if (props == null) {
            throw new AssertionError("Expected non-null request properties when connected");
        }
        // Additional checks can be added if needed
        System.out.println("testGetRequestPropertiesConnected passed.");
    }

    private static void testGetRequestPropertiesNotConnected() throws Exception {
        URL url = new URL("http://example.com");
        URLConnection conn = url.openConnection();
        // Not calling connect()
        Map<String, List<String>> props = conn.getRequestProperties();
        if (props == null) {
            throw new AssertionError("Expected non-null request properties when not connected");
        }
        System.out.println("testGetRequestPropertiesNotConnected passed.");
    }
}
