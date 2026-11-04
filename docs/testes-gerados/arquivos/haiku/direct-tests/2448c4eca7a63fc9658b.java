/*
 * @test
 * @summary Verifies URIParameter constructor null rejection and getURI behavior
 * @run main URIParameterWitupTest
 */

import java.net.URI;
import java.security.URIParameter;

public class URIParameterWitupTest {
    public static void main(String[] args) throws Exception {
        URI uri = new URI("file:/tmp/policy.txt");
        URIParameter p = new URIParameter(uri);
        if (p.getURI() != uri) {
            throw new AssertionError("getURI should return the same instance");
        }

        URI other = new URI("http://example.com/config");
        if (!other.equals(new URIParameter(other).getURI())) {
            throw new AssertionError("getURI should return the URI passed to the constructor");
        }

        try {
            new URIParameter(null);
            throw new AssertionError("expected NullPointerException for null URI");
        } catch (NullPointerException e) {
            if (!"invalid null URI".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }
}
