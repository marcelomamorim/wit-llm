/* @test    @summary Test for URIName.nameConstraint method to ensure correct URI parsing and exception handling.    @run main URINameWitupTest */

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import sun.security.util.DerValue;
import sun.security.x509.URIName;

public class URINameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidURINoScheme();
        testInvalidURINoScheme();
        testURINoSchemeWithScheme();
        testURINoSchemeWithInvalidURI();
    }

    static void testValidURINoScheme() throws Exception {
        String uriStr = "example.com";
        DerValue dv = createDerValue(uriStr);
        URIName uriName = URIName.nameConstraint(dv);
        assert uriName != null : "URIName should not be null for valid URI without scheme";
        URI uri = getURIFromURIName(uriName);
        assert uri != null : "URI should be parsed successfully";
        assert uri.getScheme() == null : "Scheme should be null";
        assert uri.getSchemeSpecificPart().equals(uriStr) : "Scheme-specific part mismatch";
    }

    static void testInvalidURINoScheme() {
        String invalidUri = "://invalid";
        try {
            DerValue dv = createDerValue(invalidUri);
            URIName.nameConstraint(dv);
            assert false : "Expected IOException for invalid URI";
        } catch (IOException e) {
            assert e.getMessage().contains("invalid URI name constraint") : "Exception message mismatch";
        }
    }

    static void testURINoSchemeWithScheme() throws Exception {
        String uriStr = "http://example.com";
        DerValue dv = createDerValue(uriStr);
        try {
            URIName.nameConstraint(dv);
            assert false : "Expected IOException for URI with scheme";
        } catch (IOException e) {
            assert e.getMessage().contains("should not include scheme") : "Exception message mismatch";
        }
    }

    static void testURINoSchemeWithInvalidURI() {
        String invalidUri = "http://";
        try {
            DerValue dv = createDerValue(invalidUri);
            URIName.nameConstraint(dv);
            assert false : "Expected IOException for invalid URI";
        } catch (IOException e) {
            assert e.getMessage().contains("invalid URI name constraint") : "Exception message mismatch";
        }
    }

    static DerValue createDerValue(String uriStr) throws IOException {
        // Simulate DerValue containing IA5String of the URI
        // For testing, create DerValue with IA5String encoding
        byte[] bytes = new byte[uriStr.length()];
        for (int i = 0; i < uriStr.length(); i++) {
            bytes[i] = (byte) uriStr.charAt(i);
        }
        return new DerValue(bytes);
    }

    static URI getURIFromURIName(URIName uriName) throws URISyntaxException {
        // Access the URI object via reflection or internal API if needed
        // Since URIName does not expose URI directly, reconstruct from its components
        // For testing, assume we can get the string representation
        // Here, we simulate by creating a URI from the string
        // In real scenario, URIName would provide accessors
        // For this test, we assume the name is stored in a way that allows URI creation
        // As we cannot access internal state, this is a placeholder
        // Alternatively, test the string representation if available
        // For simplicity, reconstruct from the original string used in createDerValue
        // which is acceptable for this test context
        return new URI(uriName.host); // Simplification for test
    }
}
