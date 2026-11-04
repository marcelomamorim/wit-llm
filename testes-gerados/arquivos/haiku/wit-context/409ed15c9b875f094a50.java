/*
 * @test
 * @summary Verifies that OIDName.subtreeDepth() unconditionally throws UnsupportedOperationException
 * @modules java.base/sun.security.x509
 * @run main OIDNameWitupTest
 */

import java.io.IOException;

import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    public static void main(String[] args) throws Exception {
        OIDName name = new OIDName("1.2.3.4");

        boolean thrown = false;
        String message = null;
        try {
            name.subtreeDepth();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            message = e.getMessage();
        }

        if (!thrown) {
            throw new RuntimeException("subtreeDepth() did not throw UnsupportedOperationException");
        }
        if (message == null || !message.contains("subtreeDepth")) {
            throw new RuntimeException("Unexpected exception message: " + message);
        }
    }
}
