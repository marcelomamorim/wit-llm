/*
 * @test
 * @summary Test sun.security.x509.AVA constructor for null argument handling and normal construction
 * @run main AVAWitupTest
 */

import sun.security.x509.AVA;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;

public class AVAWitupTest {
    public static void main(String[] args) throws Exception {
        testNullTypeThrows();
        testNullValueThrows();
        testValidConstruction();
        System.out.println("All tests passed.");
    }

    private static void testNullTypeThrows() {
        DerValue dummyVal = new DerValue(DerValue.tag_PrintableString, "dummy".getBytes());
        try {
            new AVA(null, dummyVal);
            throw new RuntimeException("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullValueThrows() {
        ObjectIdentifier dummyOid = new ObjectIdentifier(new int[] {1, 2, 3});
        try {
            new AVA(dummyOid, null);
            throw new RuntimeException("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidConstruction() {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {2, 5, 4, 3});
        DerValue val = new DerValue(DerValue.tag_PrintableString, "CN=Test".getBytes());
        AVA ava = new AVA(oid, val);
        if (ava == null) {
            throw new RuntimeException("AVA constructor returned null");
        }
    }
}
