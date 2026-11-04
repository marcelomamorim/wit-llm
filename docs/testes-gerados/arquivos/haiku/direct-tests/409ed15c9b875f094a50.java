/*
 * @test
 * @summary OIDName.subtreeDepth() must throw UnsupportedOperationException with the documented message
 * @modules java.base/sun.security.x509
 * @run main OIDNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    private static final String EXPECTED_MESSAGE = "subtreeDepth() not supported for OIDName.";

    public static void main(String[] args) throws Exception {
        checkUnsupported(new OIDName("1.2.3.4"));
        checkUnsupported(new OIDName("2.5.4.3"));
        checkUnsupported(new OIDName("1.3.6.1.5.5.7.3.1"));
        checkUnsupported(new OIDName("0.9.2342"));
    }

    private static void checkUnsupported(OIDName name) {
        try {
            int depth = name.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException, got depth " + depth);
        } catch (UnsupportedOperationException e) {
            if (!EXPECTED_MESSAGE.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
