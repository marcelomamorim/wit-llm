/*
 * @test
 * @summary Witup generated checks for DerOutputStream.putOID encoding and null handling
 * @modules java.base/sun.security.util
 * @run main DerOutputStreamWitupTest
 */

import java.util.Arrays;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;

public class DerOutputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEncodesRsaOid() throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.putOID(new ObjectIdentifier("1.2.840.113549.1.1.1"));
        byte[] expected = new byte[] {
            0x06, 0x09, 0x2A, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xF7, 0x0D, 0x01, 0x01, 0x01
        };
        check(Arrays.equals(expected, out.toByteArray()),
              "unexpected DER encoding for 1.2.840.113549.1.1.1: "
              + Arrays.toString(out.toByteArray()));
    }

    static void testEncodesSmallOid() throws Exception {
        DerOutputStream out = new DerOutputStream(16);
        out.putOID(new ObjectIdentifier("1.2.3"));
        byte[] expected = new byte[] { 0x06, 0x02, 0x2A, 0x03 };
        check(Arrays.equals(expected, out.toByteArray()),
              "unexpected DER encoding for 1.2.3: " + Arrays.toString(out.toByteArray()));
    }

    static void testAppendsAfterExistingData() throws Exception {
        DerOutputStream out = new DerOutputStream();
        out.write(0x05);
        out.write(0x00);
        out.putOID(new ObjectIdentifier("1.2.3"));
        byte[] expected = new byte[] { 0x05, 0x00, 0x06, 0x02, 0x2A, 0x03 };
        check(Arrays.equals(expected, out.toByteArray()),
              "putOID did not append after existing data: "
              + Arrays.toString(out.toByteArray()));
    }

    static void testNullOidThrowsNpe() throws Exception {
        DerOutputStream out = new DerOutputStream();
        try {
            out.putOID(null);
            throw new RuntimeException("expected NullPointerException for null oid");
        } catch (NullPointerException expected) {
            check(out.size() == 0, "stream modified after failed putOID(null)");
        }
    }

    public static void main(String[] args) throws Exception {
        testEncodesRsaOid();
        testEncodesSmallOid();
        testAppendsAfterExistingData();
        testNullOidThrowsNpe();
    }
}
