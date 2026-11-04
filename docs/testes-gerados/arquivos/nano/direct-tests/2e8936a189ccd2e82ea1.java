/* @test    @summary Test DerInputStream.getOID() method for correct ObjectIdentifier creation and exception handling    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidOID();
        testInvalidData();
    }

    private static void testValidOID() throws IOException {
        byte[] validData = {0x06, 0x03, 0x2A, 0x03, 0x04}; // DER encoding for OID 1.2.3.4
        DerInputStream dis = new DerInputStream(validData);
        ObjectIdentifier oid = dis.getOID();
        if (oid == null) {
            throw new AssertionError("getOID() returned null for valid data");
        }
        String oidStr = oid.toString();
        if (!"1.2.3.4".equals(oidStr)) {
            throw new AssertionError("Expected OID 1.2.3.4 but got " + oidStr);
        }
        System.out.println("testValidOID passed.");
    }

    private static void testInvalidData() {
        byte[] invalidData = {0x05, 0x00}; // DER encoding for NULL, not an OID
        try {
            DerInputStream dis = new DerInputStream(invalidData);
            ObjectIdentifier oid = dis.getOID();
            if (oid == null) {
                throw new AssertionError("getOID() returned null for invalid data");
            }
            String oidStr = oid.toString();
            if (!"0".equals(oidStr)) {
                throw new AssertionError("Expected OID 0 but got " + oidStr);
            }
            System.out.println("testInvalidData passed.");
        } catch (IOException e) {
            System.out.println("testInvalidData caught expected IOException: " + e.getMessage());
        }
    }
}
