/*
 * @test
 * @summary Tests URIName.nameConstraint with valid host-only constraints and invalid URI constraints
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main URINameWitupTest
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import sun.security.util.DerValue;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.URIName;

public class URINameWitupTest {

    public static void main(String[] args) throws Exception {
        testHostWithoutScheme();
        testLeadingDotHost();
        testSchemePresentIsRejected();
        testInvalidUriSyntaxIsRejected();
    }

    static void testHostWithoutScheme() throws IOException {
        URIName name = URIName.nameConstraint(new DerValue(ia5("example.com")));
        check(name != null, "expected non-null URIName");
        check(name.getType() == GeneralNameInterface.NAME_URI, "unexpected name type");
    }

    static void testLeadingDotHost() throws IOException {
        URIName name = URIName.nameConstraint(new DerValue(ia5(".example.com")));
        check(name != null, "expected non-null URIName for leading dot");
        check(name.getType() == GeneralNameInterface.NAME_URI, "unexpected name type for leading dot");
    }

    static void testSchemePresentIsRejected() throws IOException {
        expectIOException("http://example.com");
    }

    static void testInvalidUriSyntaxIsRejected() throws IOException {
        expectIOException("exa mple.com");
    }

    static void expectIOException(String value) throws IOException {
        try {
            URIName.nameConstraint(new DerValue(ia5(value)));
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("expected IOException for: " + value);
    }

    static byte[] ia5(String s) {
        byte[] b = s.getBytes(StandardCharsets.US_ASCII);
        byte[] out = new byte[b.length + 2];
        out[0] = 0x16;
        out[1] = (byte) b.length;
        System.arraycopy(b, 0, out, 2, b.length);
        return out;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
