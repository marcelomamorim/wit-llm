/*
 * @test
 * @summary Test URL.openConnection() method for different URL instances.
 * @run main URLWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLWitupTest {

    public static void main(String[] args) throws Exception {
        testOpenConnectionWithHttp();
        testOpenConnectionWithFile();
        testOpenConnectionWithCustomProtocol();
    }

    static void testOpenConnectionWithHttp() throws IOException {
        URL url = new URL("http://www.example.com");
        URLConnection conn = url.openConnection();
        if (conn == null) {
            throw new AssertionError("URLConnection should not be null for HTTP URL");
        }
        System.out.println("HTTP URL openConnection test passed.");
    }

    static void testOpenConnectionWithFile() throws IOException {
        URL url = new URL("file:///tmp/test.txt");
        URLConnection conn = url.openConnection();
        if (conn == null) {
            throw new AssertionError("URLConnection should not be null for file URL");
        }
        System.out.println("File URL openConnection test passed.");
    }

    static void testOpenConnectionWithCustomProtocol() {
        URL url = null;
        try {
            url = new URL("customprotocol://host/resource");
        } catch (Exception e) {
            throw new AssertionError("Failed to create URL with custom protocol", e);
        }
        try {
            URLConnection conn = url.openConnection();
            // Since custom protocol handler is not registered, expect an IOException or null
            if (conn != null) {
                throw new AssertionError("Expected null or exception for unhandled protocol");
            }
        } catch (IOException e) {
            System.out.println("Expected exception for unhandled protocol: " + e.getMessage());
        }
        System.out.println("Custom protocol URL openConnection test completed.");
    }
}
