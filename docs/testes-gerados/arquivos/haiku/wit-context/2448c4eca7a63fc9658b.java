/*
 * @test
 * @summary Verify URIParameter constructor null check, stored URI and Policy.Parameters contract
 * @run main URIParameterWitupTest
 */

import java.net.URI;
import java.security.URIParameter;

public class URIParameterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullUriThrowsNPE();
        testValidUriIsReturnedByGetURI();
        testImplementsPolicyParameters();
    }

    static void testNullUriThrowsNPE() {
        try {
            new URIParameter(null);
            throw new RuntimeException("Expected NullPointerException for null URI");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testValidUriIsReturnedByGetURI() throws Exception {
        URI uri = new URI("file:/tmp/policy.txt");
        URIParameter param = new URIParameter(uri);
        if (param.getURI() != uri) {
            throw new RuntimeException("getURI() did not return the same URI instance");
        }
        if (!uri.equals(param.getURI())) {
            throw new RuntimeException("getURI() returned a different URI");
        }
    }

    static void testImplementsPolicyParameters() throws Exception {
        URIParameter param = new URIParameter(new URI("file:/tmp/policy.txt"));
        if (!(param instanceof java.security.Policy.Parameters)) {
            throw new RuntimeException("URIParameter must implement Policy.Parameters");
        }
    }
}
