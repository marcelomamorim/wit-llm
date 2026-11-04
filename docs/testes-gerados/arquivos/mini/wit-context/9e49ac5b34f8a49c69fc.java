/*
 * @test
 * @summary Test URICertStoreParameters constructor behavior including null URI rejection
 * @run main URICertStoreParametersWitupTest
 */

import java.net.URI;
import java.security.cert.URICertStoreParameters;

public class URICertStoreParametersWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidURI();
        testConstructorWithNullURI();
    }

    private static void testConstructorWithValidURI() {
        try {
            URI uri = new URI("http://example.com/certs");
            URICertStoreParameters params = new URICertStoreParameters(uri);
            if (!uri.equals(params.getURI())) {
                throw new RuntimeException("getURI() did not return the URI passed to constructor");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testConstructorWithValidURI", e);
        }
    }

    private static void testConstructorWithNullURI() {
        try {
            new URICertStoreParameters(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type thrown", e);
        }
    }
}
