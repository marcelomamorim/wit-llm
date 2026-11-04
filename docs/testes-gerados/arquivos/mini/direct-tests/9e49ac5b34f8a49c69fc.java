/*
 * @test
 * @summary Test constructor of URICertStoreParameters for null and valid URI
 * @run main URICertStoreParametersWitupTest
 */

import java.net.URI;
import java.security.cert.URICertStoreParameters;

public class URICertStoreParametersWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidURI();
        testConstructorWithNullURI();
    }

    private static void testConstructorWithValidURI() throws Exception {
        URI uri = new URI("http://example.com/certs");
        URICertStoreParameters params = new URICertStoreParameters(uri);
        if (!params.getURI().equals(uri)) {
            throw new RuntimeException("getURI() did not return the URI passed to constructor");
        }
    }

    private static void testConstructorWithNullURI() {
        try {
            new URICertStoreParameters(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
