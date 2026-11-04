/*
 * @test
 * @summary Test sun.security.util.DerValue.getOID() behavior and exception
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetOIDWithValidTag();
        testGetOIDWithInvalidTag();
    }

    private static void testGetOIDWithValidTag() throws Exception {
        // Construct a DerValue with tag_ObjectId (6) and minimal valid buffer
        // Using constructor DerValue(byte tag, byte[] data)
        // Data must be a valid DER encoding of an OID; minimal valid OID encoding: 06 01 00
        byte[] oidEncoding = new byte[] { 0x06, 0x01, 0x00 };
        DerValue derValue = new DerValue((byte)6, oidEncoding);

        ObjectIdentifier oid = derValue.getOID();
        if (oid == null) {
            throw new RuntimeException("getOID() returned null for valid tag");
        }
    }

    private static void testGetOIDWithInvalidTag() {
        // Construct a DerValue with a tag different from 6
        byte[] dummyData = new byte[] { 0x01, 0x01, 0x00 };
        DerValue derValue = new DerValue((byte)1, dummyData); // tag 1 is BOOLEAN

        try {
            derValue.getOID();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not an OID")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
