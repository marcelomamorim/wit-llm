/*
 * @test
 * @summary Test sun.security.x509.RDN constructor with AVA argument
 * @run main RDNWitupTest
 */

import sun.security.x509.RDN;
import sun.security.x509.AVA;

public class RDNWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidAVA();
        testConstructorWithNullAVA();
    }

    private static void testConstructorWithValidAVA() {
        AVA ava = new AVA("CN=Test");
        RDN rdn = new RDN(ava);
        AVA[] assertions = rdn.assertion;
        if (assertions == null || assertions.length != 1 || assertions[0] != ava) {
            throw new AssertionError("RDN assertion array does not contain the expected AVA");
        }
    }

    private static void testConstructorWithNullAVA() {
        try {
            new RDN(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
