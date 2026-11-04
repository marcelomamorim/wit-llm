/* @test    @summary Test the toURL() method of java.net.URI for various URI instances    @run main URIWitupTest */

import java.net.URI;
import java.net.URL;
import java.net.MalformedURLException;

public class URIWitupTest {

    public static void main(String[] args) {
        testToURLWithHttpURI();
        testToURLWithFileURI();
        testToURLWithMailtoURI();
        testToURLWithURNURI();
        testToURLWithRelativeURI();
    }

    static void testToURLWithHttpURI() {
        try {
            URI uri = new URI("http://example.com/path?query#fragment");
            URL url = uri.toURL();
            assert url != null : "URL should not be null for HTTP URI";
            assert url.toString().equals("http://example.com/path?query#fragment") : "Unexpected URL string";
        } catch (Exception e) {
            throw new AssertionError("Exception in testToURLWithHttpURI", e);
        }
    }

    static void testToURLWithFileURI() {
        try {
            URI uri = new URI("file:///C:/path/to/file.txt");
            URL url = uri.toURL();
            assert url != null : "URL should not be null for file URI";
            assert url.toString().equals("file:///C:/path/to/file.txt") : "Unexpected URL string";
        } catch (Exception e) {
            throw new AssertionError("Exception in testToURLWithFileURI", e);
        }
    }

    static void testToURLWithMailtoURI() {
        try {
            URI uri = new URI("mailto:someone@example.com");
            URL url = uri.toURL();
            assert url != null : "URL should not be null for mailto URI";
            assert url.toString().equals("mailto:someone@example.com") : "Unexpected URL string";
        } catch (Exception e) {
            throw new AssertionError("Exception in testToURLWithMailtoURI", e);
        }
    }

    static void testToURLWithURNURI() {
        try {
            URI uri = new URI("urn:isbn:0451450523");
            URL url = uri.toURL();
            assert url != null : "URL should not be null for URN URI";
            assert url.toString().equals("urn:isbn:0451450523") : "Unexpected URL string";
        } catch (Exception e) {
            throw new AssertionError("Exception in testToURLWithURNURI", e);
        }
    }

    static void testToURLWithRelativeURI() {
        try {
            URI uri = new URI("../relative/path");
            URL url = uri.toURL();
            assert url != null : "URL should not be null for relative URI";
            assert url.toString().equals("../relative/path") : "Unexpected URL string";
        } catch (MalformedURLException e) {
            // Expected exception because relative URI cannot be converted to URL
            // in Java, toURL() throws MalformedURLException for relative URIs
            // So, this test expects an exception here
            return;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testToURLWithRelativeURI", e);
        }
        throw new AssertionError("Expected MalformedURLException for relative URI")
    }
}
