/*
 * @test
 * @summary Test java.net.URI.toURL() method behavior
 * @run main URIWitupTest
 */

import java.net.URI;
import java.net.URL;
import java.net.MalformedURLException;

public class URIWitupTest {
    public static void main(String[] args) throws Exception {
        testToURLWithValidURI();
        testToURLWithOpaqueURI();
        testToURLWithRelativeURI();
    }

    private static void testToURLWithValidURI() throws Exception {
        URI uri = new URI("http://example.com/path");
        URL url = uri.toURL();
        if (!"http".equals(url.getProtocol())) {
            throw new RuntimeException("Expected protocol 'http' but got: " + url.getProtocol());
        }
        if (!"example.com".equals(url.getHost())) {
            throw new RuntimeException("Expected host 'example.com' but got: " + url.getHost());
        }
        if (!"/path".equals(url.getPath())) {
            throw new RuntimeException("Expected path '/path' but got: " + url.getPath());
        }
    }

    private static void testToURLWithOpaqueURI() throws Exception {
        URI uri = new URI("mailto:java-net@java.sun.com");
        try {
            uri.toURL();
            throw new RuntimeException("Expected MalformedURLException for opaque URI but none thrown");
        } catch (MalformedURLException e) {
            // Expected exception
        }
    }

    private static void testToURLWithRelativeURI() throws Exception {
        URI uri = new URI("/relative/path");
        try {
            uri.toURL();
            throw new RuntimeException("Expected MalformedURLException for relative URI but none thrown");
        } catch (MalformedURLException e) {
            // Expected exception
        }
    }
}
