/* @test    @summary Test for DerValue.getOID() method to verify exception handling and normal behavior    @run main DerValueWitupTest */

import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class DerValueWitupTest {

    public static void main(String[] args) throws Exception {
        testGetOIDWithValidTag();
        testGetOIDWithInvalidTag();
    }

    private static void testGetOIDWithValidTag() throws Exception {
        byte[] oidBytes = {0x06, 0x03, 0x2A, 0x03, 0x04}; // ASN.1 encoding for OID 1.2.3.4
        DerValue dv = new DerValue(oidBytes);
        // Manually set tag to tag_ObjectId to simulate valid OID
        dv.tag = DerValue.tag_ObjectId;
        ObjectIdentifier oid = dv.getOID();
        if (oid == null) {
            throw new AssertionError("Expected non-null ObjectIdentifier");
        }
        System.out.println("testGetOIDWithValidTag passed: " + oid);
    }

    private static void testGetOIDWithInvalidTag() {
        byte[] invalidBytes = {0x05, 0x00}; // ASN.1 NULL
        DerValue dv = new DerValue(invalidBytes);
        dv.tag = 0x05; // NULL tag
        try {
            dv.getOID();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // Expected exception
            System.out.println("testGetOIDWithInvalidTag passed: " + e.getMessage());
        }
    }
}
