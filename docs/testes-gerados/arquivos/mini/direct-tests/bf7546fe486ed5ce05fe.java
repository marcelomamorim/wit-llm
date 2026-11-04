/*
 * @test
 * @summary Test KeyIdentifier constructor with DerValue input
 * @run main KeyIdentifierWitupTest
 */

import sun.security.x509.KeyIdentifier;
import sun.security.util.DerValue;
import java.io.IOException;

public class KeyIdentifierWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testEmptyDerValue();
    }

    private static void testValidDerValue() throws IOException {
        // Create a DerValue with a known octet string
        byte[] octets = new byte[] {1, 2, 3, 4, 5};
        DerValue val = new DerValue(DerValue.tag_OctetString, octets);

        KeyIdentifier keyId = new KeyIdentifier(val);
        byte[] id = keyId.getIdentifier();

        // Check that the returned identifier matches the original octets
        if (id.length != octets.length) {
            throw new RuntimeException("Length mismatch");
        }
        for (int i = 0; i < id.length; i++) {
            if (id[i] != octets[i]) {
                throw new RuntimeException("Byte mismatch at index " + i);
            }
        }
    }

    private static void testEmptyDerValue() throws IOException {
        // Create a DerValue with empty octet string
        byte[] octets = new byte[0];
        DerValue val = new DerValue(DerValue.tag_OctetString, octets);

        KeyIdentifier keyId = new KeyIdentifier(val);
        byte[] id = keyId.getIdentifier();

        if (id.length != 0) {
            throw new RuntimeException("Expected empty identifier");
        }
    }
}
