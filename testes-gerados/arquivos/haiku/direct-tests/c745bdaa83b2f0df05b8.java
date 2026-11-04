/*
 * @test
 * @summary Basic behavior and null-parameter handling of OtherName(ObjectIdentifier, byte[])
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main OtherNameWitupTest
 */

import java.io.IOException;
import java.util.Arrays;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.OtherName;

public class OtherNameWitupTest {

    public static void main(String[] args) throws Exception {
        testStoresOidAndValue();
        testGetNameValueReturnsCopy();
        testNullOidThrowsNpe();
        testNullValueThrowsNpe();
        testUnrecognizedOidAccepted();
    }

    static void testStoresOidAndValue() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        byte[] value = {0x04, 0x01, 0x41};
        OtherName on = new OtherName(oid, value);
        if (!oid.equals(on.getOID())) {
            throw new RuntimeException("getOID() does not return the constructor OID");
        }
        if (!Arrays.equals(value, on.getNameValue())) {
            throw new RuntimeException("getNameValue() does not match the constructor value");
        }
    }

    static void testGetNameValueReturnsCopy() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        byte[] value = {0x04, 0x01, 0x41};
        OtherName on = new OtherName(oid, value);
        byte[] copy = on.getNameValue();
        copy[0] = 0x00;
        if (on.getNameValue()[0] != 0x04) {
            throw new RuntimeException("getNameValue() must return a defensive copy");
        }
    }

    static void testNullOidThrowsNpe() throws IOException {
        boolean thrown = false;
        try {
            new OtherName((ObjectIdentifier) null, new byte[] {0x04, 0x00});
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("null oid must raise NullPointerException");
        }
    }

    static void testNullValueThrowsNpe() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        boolean thrown = false;
        try {
            new OtherName(oid, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("null value must raise NullPointerException");
        }
    }

    static void testUnrecognizedOidAccepted() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4.5.6.7.8.9");
        byte[] value = {0x02, 0x01, 0x05};
        OtherName on = new OtherName(oid, value);
        if (!oid.equals(on.getOID())) {
            throw new RuntimeException("unrecognized OID must still be stored");
        }
        if (!Arrays.equals(value, on.getNameValue())) {
            throw new RuntimeException("unrecognized OID value must still be stored");
        }
    }
}
