/*
 * @test
 * @summary Test sun.security.x509.OIDName.subtreeDepth() throws UnsupportedOperationException
 * @run main OIDNameWitupTest
 */

import sun.security.x509.OIDName;

public class OIDNameWitupTest {
    public static void main(String[] args) {
        OIDName oidName = new OIDName("1.2.3");
        try {
            oidName.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("subtreeDepth() not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
