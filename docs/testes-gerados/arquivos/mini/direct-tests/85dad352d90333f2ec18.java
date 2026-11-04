/*
 * @test
 * @summary Test constructor OIDName(DerValue derValue) for correct OID extraction and exception handling
 * @run main OIDNameWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.OIDName;

public class OIDNameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testDerValueWithNullOID();
    }

    private static void testValidDerValue() throws Exception {
        // Create a DerValue with a known OID
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        DerValue derValue = new DerValue(oid);

        OIDName oidName = new OIDName(derValue);
        ObjectIdentifier extractedOid = oidName.getOID();

        if (!oid.equals(extractedOid)) {
            throw new RuntimeException("OID extracted does not match expected OID");
        }

        // Also test toString returns expected format
        String str = oidName.toString();
        if (!str.contains(oid.toString())) {
            throw new RuntimeException("toString does not contain OID string");
        }
    }

    private static void testDerValueWithNullOID() {
        // DerValue subclass that returns null for getOID to simulate error
        DerValue derValue = new DerValue(null) {
            @Override
            public ObjectIdentifier getOID() throws IOException {
                return null;
            }
        };

        try {
            new OIDName(derValue);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected because oid is null and OIDName does not check for null
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException thrown", e);
        }
    }
}
