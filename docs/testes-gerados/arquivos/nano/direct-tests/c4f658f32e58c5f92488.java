/* @test    @summary Test DerValue.getOID() method for correct behavior and exception handling    @run main DerValueWitupTest */

import sun.security.util.DerValue;
import java.io.IOException;

public class DerValueWitupTest {

    public static void main(String[] args) {
        testGetOIDWithValidOID();
        testGetOIDWithInvalidTag();
    }

    private static void testGetOIDWithValidOID() {
        try {
            byte[] oidBytes = {0x06, 0x03, 0x2A, 0x03, 0x04}; // OID: 1.2.3.4
            DerValue dv = new DerValue(oidBytes);
            dv.tag = DerValue.tag_ObjectId; // set tag to ObjectId
            ObjectIdentifier oid = dv.getOID();
            if (oid == null) {
                throw new AssertionError("Expected non-null ObjectIdentifier");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    private static void testGetOIDWithInvalidTag() {
        try {
            byte[] data = {0x02, 0x01, 0x01}; // INTEGER tag
            DerValue dv = new DerValue(data);
            dv.tag = 0x02; // set to INTEGER tag
            dv.getOID();
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("not an OID")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
