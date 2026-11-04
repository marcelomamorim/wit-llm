/* @test    @summary Test for sun.security.x509.URIName.nameConstraint(DerValue) method    @run main URINameWitupTest */

import java.io.IOException;
import java.net.URI;
import sun.security.util.DerValue;

public class URINameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidURINoScheme();
        testInvalidURISyntax();
        testURIWithoutScheme();
        testURIWithScheme();
        testURIWithSchemeAndHost();
    }

    static void testValidURINoScheme() throws Exception {
        String uriStr = "example.com";
        DerValue dv = createDerValueFromString(uriStr);
        URIName result = URIName.nameConstraint(dv);
        assert result != null : "Expected non-null URIName for valid URI without scheme";
    }

    static void testInvalidURISyntax() {
        String invalidUriStr = "ht!tp://invalid";
        try {
            DerValue dv = createDerValueFromString(invalidUriStr);
            URIName.nameConstraint(dv);
            assert false : "Expected IOException for invalid URI syntax";
        } catch (IOException e) {
            // expected
        }
    }

    static void testURIWithoutScheme() throws Exception {
        String uriStr = "//host/path";
        DerValue dv = createDerValueFromString(uriStr);
        try {
            URIName.nameConstraint(dv);
            assert false : "Expected IOException for URI with scheme";
        } catch (IOException e) {
            // expected
        }
    }

    static void testURIWithSchemeAndHost() throws Exception {
        String uriStr = "http://example.com";
        DerValue dv = createDerValueFromString(uriStr);
        try {
            URIName.nameConstraint(dv);
            assert false : "Expected IOException for URI with scheme";
        } catch (IOException e) {
            // expected
        }
    }

    static DerValue createDerValueFromString(String str) throws IOException {
        // Simulate DerValue creation from string; in real tests, this would be more complex
        // For testing, we assume DerValue can be constructed from IA5String directly
        // Here, we mock this behavior by creating a DerValue with the string as IA5String
        // Note: In real scenarios, proper DER encoding is needed
        return new DerValue((byte) 22, str.getBytes());
    }
}
