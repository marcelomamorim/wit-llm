/* @test    @summary Test AVA constructor with valid ObjectIdentifier and DerValue    @run main AVAWitupTest */

import sun.security.x509.AVA;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;

public class AVAWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullTypeThrows();
        testNullValueThrows();
    }

    static void testValidConstructor() {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.3");
        byte[] valBytes = {0x13, 0x02, 0x41, 0x42}; // ASN.1 PrintableString "AB"
        DerValue derVal = new DerValue(valBytes);
        AVA ava = new AVA(oid, derVal);
        assert ava != null : "AVA instance should not be null";
        assert ava.oid.equals(oid) : "OID should match";
        assert ava.value.equals(derVal) : "DerValue should match";
    }

    static void testNullTypeThrows() {
        try {
            new AVA(null, new DerValue(new byte[]{0x13, 0x02, 0x41, 0x42}));
            assert false : "Expected NullPointerException for null type";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullValueThrows() {
        try {
            new AVA(new ObjectIdentifier("2.5.4.3"), null);
            assert false : "Expected NullPointerException for null value";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
