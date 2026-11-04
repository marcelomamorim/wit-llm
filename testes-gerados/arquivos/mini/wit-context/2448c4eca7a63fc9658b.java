/*
 * @test
 * @summary Test constructor and getURI method of java.security.URIParameter
 * @run main URIParameterWitupTest
 */

import java.net.URI;
import java.security.URIParameter;

public class URIParameterWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidURI();
        testConstructorWithNullURI();
    }

    private static void testConstructorWithValidURI() throws Exception {
        URI uri = new URI("http://example.com");
        URIParameter param = new URIParameter(uri);
        URI returnedURI = param.getURI();
        if (!uri.equals(returnedURI)) {
            throw new RuntimeException("getURI() did not return the URI passed to constructor");
        }
    }

    private static void testConstructorWithNullURI() {
        try {
            new URIParameter(null);
            throw new RuntimeException("Constructor did not throw NullPointerException when passed null");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("invalid null URI")) {
                throw new RuntimeException("NullPointerException message unexpected: " + msg);
            }
        }
    }
}
