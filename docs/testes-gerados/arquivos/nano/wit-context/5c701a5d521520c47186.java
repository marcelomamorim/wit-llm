/* @test    @summary Test for sun.security.x509.AVA.derEncode method behavior.    @run main AVAWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.AVA;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;

public class AVAWitupTest {

    public static void main(String[] args) throws Exception {
        testDerEncodeNormal();
        testDerEncodeNullOut();
        testDerEncodeInvalidOid();
    }

    static void testDerEncodeNormal() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        DerValue value = new DerValue(new byte[]{0x01, 0x02}); // simple DER value
        AVA ava = new AVA(oid, value);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ava.derEncode(baos);
        byte[] result = baos.toByteArray();
        if (result == null || result.length == 0) {
            throw new AssertionError("derEncode returned empty result");
        }
        System.out.println("testDerEncodeNormal passed, output length: " + result.length);
    }

    static void testDerEncodeNullOut() {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        DerValue value = new DerValue(new byte[]{0x01, 0x02});
        AVA ava = new AVA(oid, value);
        try {
            ava.derEncode(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testDerEncodeNullOut passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testDerEncodeInvalidOid() throws Exception {
        ObjectIdentifier invalidOid = null;
        DerValue value = new DerValue(new byte[]{0x01, 0x02});
        try {
            AVA ava = new AVA(invalidOid, value);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ava.derEncode(baos);
            throw new AssertionError("Expected NullPointerException for null OID not thrown");
        } catch (NullPointerException e) {
            System.out.println("testDerEncodeInvalidOid passed");
        }
    }
}
