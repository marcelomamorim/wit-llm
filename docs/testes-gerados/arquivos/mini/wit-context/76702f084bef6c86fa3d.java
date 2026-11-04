/*
 * @test
 * @summary Test sun.security.x509.RDN constructor with AVA parameter for null and valid input
 * @run main RDNWitupTest
 */

import sun.security.x509.RDN;
import sun.security.x509.AVA;

public class RDNWitupTest {
    public static void main(String[] args) throws Exception {
        testNullAVAThrows();
        testValidAVAConstructs();
    }

    private static void testNullAVAThrows() {
        try {
            new RDN((AVA) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidAVAConstructs() {
        AVA ava = new AVA("CN=Test");
        RDN rdn = new RDN(ava);
        if (rdn == null) {
            throw new RuntimeException("RDN constructor returned null");
        }
    }
}
