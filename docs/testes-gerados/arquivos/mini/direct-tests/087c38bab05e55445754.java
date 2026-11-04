/*
 * @test
 * @summary Test sun.security.x509.AVA constructor behavior
 * @run main AVAWitupTest
 */

import sun.security.x509.AVA;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;

public class AVAWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidArgs();
        testConstructorWithNullType();
        testConstructorWithNullValue();
    }

    private static void testConstructorWithValidArgs() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3});
        DerValue val = new DerValue(DerValue.tag_PrintableString, "test".getBytes("UTF-8"));
        AVA ava = new AVA(oid, val);
        if (ava == null) {
            throw new RuntimeException("AVA constructor returned null");
        }
        if (!ava.oid.equals(oid)) {
            throw new RuntimeException("AVA oid field not set correctly");
        }
        if (!ava.value.equals(val)) {
            throw new RuntimeException("AVA value field not set correctly");
        }
    }

    private static void testConstructorWithNullType() {
        DerValue val = new DerValue(DerValue.tag_PrintableString, "test".getBytes());
        try {
            new AVA(null, val);
            throw new RuntimeException("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorWithNullValue() {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3});
        try {
            new AVA(oid, null);
            throw new RuntimeException("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
