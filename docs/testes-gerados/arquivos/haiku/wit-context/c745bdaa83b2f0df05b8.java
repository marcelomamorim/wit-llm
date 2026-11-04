/*
 * @test
 * @summary Verify OtherName constructor null-parameter checks and observable getters
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main OtherNameWitupTest
 */

import sun.security.util.ObjectIdentifier;
import sun.security.x509.OtherName;

public class OtherNameWitupTest {

    public static void main(String[] args) throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        byte[] value = {0x04, 0x01, 0x41};

        testNullOid(value);
        testNullValue(oid);
        testNullBoth();
        testGetOidReturnsConstructorOid(oid, value);
        testGetNameValueReturnsCopy(oid, value);
    }

    static void testNullOid(byte[] value) throws Exception {
        try {
            new OtherName(null, value);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null oid");
    }

    static void testNullValue(ObjectIdentifier oid) throws Exception {
        try {
            new OtherName(oid, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null value");
    }

    static void testNullBoth() throws Exception {
        try {
            new OtherName(null, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null oid and value");
    }

    static void testGetOidReturnsConstructorOid(ObjectIdentifier oid, byte[] value) throws Exception {
        OtherName on = new OtherName(oid, value);
        if (!oid.equals(on.getOID())) {
            throw new RuntimeException("getOID() does not return the constructor oid");
        }
    }

    static void testGetNameValueReturnsCopy(ObjectIdentifier oid, byte[] value) throws Exception {
        OtherName on = new OtherName(oid, value);
        byte[] first = on.getNameValue();
        if (!java.util.Arrays.equals(value, first)) {
            throw new RuntimeException("getNameValue() content mismatch");
        }
        first[0] = (byte) 0xFF;
        byte[] second = on.getNameValue();
        if (second[0] != value[0]) {
            throw new RuntimeException("getNameValue() exposes internal array");
        }
    }
}
