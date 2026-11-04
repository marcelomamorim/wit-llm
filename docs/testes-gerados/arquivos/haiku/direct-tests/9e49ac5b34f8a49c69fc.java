/*
 * @test
 * @summary Verifies URICertStoreParameters constructor null handling and URI retention
 * @run main URICertStoreParametersWitupTest
 */

import java.net.URI;
import java.security.cert.URICertStoreParameters;

public class URICertStoreParametersWitupTest {

    public static void main(String[] args) throws Exception {
        URI uri = new URI("ldap://example.com/cn=certs");

        URICertStoreParameters p = new URICertStoreParameters(uri);
        if (p.getURI() != uri) {
            throw new AssertionError("getURI() should return the URI passed to the constructor");
        }

        URICertStoreParameters copy = p.clone();
        if (copy.getURI() != uri) {
            throw new AssertionError("clone() should retain the same URI");
        }

        try {
            new URICertStoreParameters(null);
            throw new AssertionError("Expected NullPointerException for null URI");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
