/*
 * @test
 * @summary Verify that RDN(AVA) rejects a null AVA with NullPointerException
 * @modules java.base/sun.security.x509
 * @run main RDNWitupTest
 */

import sun.security.x509.AVA;
import sun.security.x509.RDN;

public class RDNWitupTest {

    public static void main(String[] args) throws Exception {
        try {
            new RDN((AVA) null);
            throw new AssertionError("Expected NullPointerException for null AVA");
        } catch (NullPointerException expected) {
            // expected behavior
        }
    }
}
