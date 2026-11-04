/* @test    @summary Test RDN constructor with a non-null AVA object    @run main RDNWitupTest */

import sun.security.x509.RDN;
import sun.security.util.AVA;

public class RDNWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidAVA();
        testConstructorWithNullAVA();
    }

    static void testConstructorWithValidAVA() {
        AVA ava = new AVA(); // Assuming default constructor exists for test purposes
        RDN rdn = new RDN(ava);
        assert rdn != null : "RDN should not be null when constructed with a valid AVA";
        assert rdn.assertion.length == 1 : "RDN should contain exactly one AVA";
        assert rdn.assertion[0] == ava : "The AVA in RDN should be the same as the one provided";
    }

    static void testConstructorWithNullAVA() {
        try {
            new RDN((AVA) null);
            assert false : "Constructor should throw NullPointerException when AVA is null";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
