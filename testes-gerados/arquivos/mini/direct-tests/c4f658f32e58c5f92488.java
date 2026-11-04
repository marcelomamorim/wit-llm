/*
 * @test
 * @summary Test sun.security.util.DerValue.getOID() method behavior
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.ByteArrayInputStream;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetOIDWithValidOID();
        testGetOIDWithInvalidTag();
    }

    private static void testGetOIDWithValidOID() throws Exception {
        // Construct a DerValue with tag_ObjectId and valid OID encoding
        // OID encoding for 1.2.3 (arbitrary valid OID)
        byte[] oidEncoded = new byte[] { 0x06, 0x03, 0x2a, 0x03, 0x03 };
        // The first byte 0x06 is the tag for OBJECT IDENTIFIER
        // The second byte 0x03 is the length
        // The rest is the OID value

        // We create DerValue from the full encoding
        DerValue derValue = new DerValue(oidEncoded);

        // The tag field should be tag_ObjectId (0x06)
        if (derValue.tag != DerValue.tag_ObjectId) {
            throw new RuntimeException("DerValue tag is not tag_ObjectId as expected");
        }

        ObjectIdentifier oid = derValue.getOID();
        if (oid == null) {
            throw new RuntimeException("getOID() returned null");
        }

        // The string representation should start with "1.2.3"
        String oidStr = oid.toString();
        if (!oidStr.startsWith("1.2.3")) {
            throw new RuntimeException("Unexpected OID string: " + oidStr);
        }
    }

    private static void testGetOIDWithInvalidTag() {
        // Construct a DerValue with a tag different from tag_ObjectId
        // Use tag_Integer (0x02) with some dummy data
        byte[] intEncoded = new byte[] { 0x02, 0x01, 0x01 };
        DerValue derValue = new DerValue(intEncoded);

        try {
            derValue.getOID();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not an OID")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
