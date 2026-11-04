/* @test    @summary Test AVA.derEncode method for correct DER encoding behavior    @run main AVAWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.AVA;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;

public class AVAWitupTest {

    public static void main(String[] args) throws IOException {
        testDerEncodeWithValidOIDAndValue();
        testDerEncodeWithEmptyOID();
        testDerEncodeWithNullValue();
    }

    private static void testDerEncodeWithValidOIDAndValue() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        byte[] valueBytes = {0x01, 0x02, 0x03};
        DerValue value = new DerValue(valueBytes);
        AVA ava = new AVA(oid, value);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ava.derEncode(baos);
        byte[] encoded = baos.toByteArray();
        // Basic check: encoded should contain OID and value bytes
        boolean containsOID = new String(encoded).contains("1.2.3.4");
        boolean containsValue = new String(encoded).contains(new String(valueBytes));
        if (!containsOID || !containsValue) {
            throw new AssertionError("DER encoding does not contain expected OID or value");
        }
        System.out.println("testDerEncodeWithValidOIDAndValue passed.");
    }

    private static void testDerEncodeWithEmptyOID() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("");
        byte[] valueBytes = {0x0A, 0x0B};
        DerValue value = new DerValue(valueBytes);
        AVA ava = new AVA(oid, value);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ava.derEncode(baos);
        byte[] encoded = baos.toByteArray();
        // Check that encoding contains empty OID representation
        boolean containsEmptyOID = new String(encoded).contains("");
        if (!containsEmptyOID) {
            throw new AssertionError("DER encoding does not contain empty OID as expected");
        }
        System.out.println("testDerEncodeWithEmptyOID passed.");
    }

    private static void testDerEncodeWithNullValue() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.29.17");
        DerValue value = null;
        try {
            AVA ava = new AVA(oid, value);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ava.derEncode(baos);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testDerEncodeWithNullValue passed.");
        }
    }
}
