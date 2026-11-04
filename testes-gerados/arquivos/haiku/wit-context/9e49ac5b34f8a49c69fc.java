/*
 * @test
 * @summary Verifies URICertStoreParameters constructor null-guard and getURI observable behavior
 * @run main URICertStoreParametersWitupTest
 */

import java.net.URI;
import java.security.cert.URICertStoreParameters;

public class URICertStoreParametersWitupTest {

    public static void main(String[] args) throws Exception {
        testNullUriThrowsNullPointerException();
        testValidUriIsReturnedByGetURI();
        testGetURIReturnsSameInstancePassedToConstructor();
    }

    private static void testNullUriThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new URICertStoreParameters(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null URI");
        }
    }

    private static void testValidUriIsReturnedByGetURI() throws Exception {
        URI uri = new URI("ldap://example.com/cn=certs");
        URICertStoreParameters params = new URICertStoreParameters(uri);
        if (!uri.equals(params.getURI())) {
            throw new RuntimeException("getURI() did not return the URI supplied at construction");
        }
    }

    private static void testGetURIReturnsSameInstancePassedToConstructor() throws Exception {
        URI uri = new URI("http://example.com/crl.crl");
        URICertStoreParameters params = new URICertStoreParameters(uri);
        if (params.getURI() != uri) {
            throw new RuntimeException("getURI() did not return the same URI instance");
        }
    }
}
