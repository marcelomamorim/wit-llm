/* @test    @summary Test URIParameter constructor with valid URI    @run main URIParameterWitupTest */

import java.net.URI;
import java.security.URIParameter;

public class URIParameterWitupTest {

    public static void main(String[] args) {
        testValidURI();
        testNullURI();
    }

    private static void testValidURI() {
        URI uri = URI.create("http://example.com/data");
        URIParameter param = new URIParameter(uri);
        assert param.getURI().equals(uri) : "URI should match the input";
    }

    private static void testNullURI() {
        boolean exceptionThrown = false;
        try {
            new URIParameter(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
            assert e.getMessage().equals("invalid null URI") : "Exception message should be 'invalid null URI'";
        }
        assert exceptionThrown : "NullPointerException should be thrown for null URI";
    }
}
