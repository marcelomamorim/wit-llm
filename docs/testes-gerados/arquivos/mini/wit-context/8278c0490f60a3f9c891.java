/*
 * @test
 * @summary Test java.net.URI.toURL() method behavior including IllegalArgumentException on null scheme
 * @run main java.net.URIWitupTest
 */

import java.net.URI;
import java.net.URL;
import java.net.MalformedURLException;

public class URIWitupTest {
    public static void main(String[] args) throws Exception {
        testToURLNormal();
        testToURLNullScheme();
    }

    private static void testToURLNormal() throws Exception {
        // Normal URI with scheme should convert to URL
        URI uri = new URI("http://example.com");
        URL url = uri.toURL();
        if (!"http".equals(url.getProtocol())) {
            throw new AssertionError("Expected protocol 'http', got: " + url.getProtocol());
        }
        if (!"example.com".equals(url.getHost())) {
            throw new AssertionError("Expected host 'example.com', got: " + url.getHost());
        }
    }

    private static void testToURLNullScheme() {
        // Construct URI with null scheme to trigger IllegalArgumentException in toURL()
        // Using public constructor URI(String scheme, String ssp, String fragment) with null scheme
        try {
            URI uri = new URI(null, "//example.com", null);
            uri.toURL();
            throw new AssertionError("Expected IllegalArgumentException due to null scheme");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (MalformedURLException e) {
            throw new AssertionError("Unexpected MalformedURLException instead of IllegalArgumentException");
        }
    }
}
