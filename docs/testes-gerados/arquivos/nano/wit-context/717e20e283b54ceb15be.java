/*
 * @test
 * @summary Test for HttpTimestamper constructor with various URIs
 * @run main HttpTimestamperWitupTest
 */

import java.net.URI;
import java.net.URISyntaxException;
import sun.security.timestamp.HttpTimestamper;

public class HttpTimestamperWitupTest {

    public static void main(String[] args) {
        testValidHttpUri();
        testValidHttpsUri();
        testInvalidUriScheme();
    }

    static void testValidHttpUri() {
        try {
            URI uri = new URI("http://example.com/tsa");
            HttpTimestamper ht = new HttpTimestamper(uri);
            System.out.println("testValidHttpUri passed");
        } catch (Exception e) {
            System.err.println("testValidHttpUri failed: " + e);
        }
    }

    static void testValidHttpsUri() {
        try {
            URI uri = new URI("https://example.com/tsa");
            HttpTimestamper ht = new HttpTimestamper(uri);
            System.out.println("testValidHttpsUri passed");
        } catch (Exception e) {
            System.err.println("testValidHttpsUri failed: " + e);
        }
    }

    static void testInvalidUriScheme() {
        try {
            URI uri = new URI("ftp://example.com/tsa");
            HttpTimestamper ht = new HttpTimestamper(uri);
            System.err.println("testInvalidUriScheme failed: exception expected but not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidUriScheme passed");
        } catch (Exception e) {
            System.err.println("testInvalidUriScheme failed: " + e);
        }
    }
}
