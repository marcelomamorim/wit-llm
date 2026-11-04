/* @test    @summary Test OIDName constructor with DerValue input    @run main OIDNameWitupTest */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidDerValue();
        testConstructorWithInvalidString();
    }

    static void testConstructorWithValidDerValue() throws IOException {
        // Create a DerValue with a valid OID string
        DerValue derValue = new DerValue();
        derValue.setOID("1.2.3.4");
        OIDName oidName = new OIDName(derValue);
        assert "OIDName: 1.2.3.4".equals(oidName.toString()) : "Expected string representation to match";
        assert oidName.getOID().toString().equals("1.2.3.4") : "OID should be 1.2.3.4";
    }

    static void testConstructorWithInvalidString() {
        try {
            new OIDName("invalid\x00oid");
            assert false : "Expected IOException for invalid OID string";
        } catch (IOException e) {
            // Expected exception
        }
    }
}
