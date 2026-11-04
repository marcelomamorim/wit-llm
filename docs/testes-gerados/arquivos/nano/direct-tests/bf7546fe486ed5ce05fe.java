/* @test    @summary Test constructor KeyIdentifier(DerValue) for sun.security.x509.KeyIdentifier    @run main KeyIdentifierWitupTest */

import sun.security.util.DerValue;
import sun.security.x509.KeyIdentifier;
import java.io.IOException;

public class KeyIdentifierWitupTest {

    public static void main(String[] args) throws IOException {
        testValidDerValue();
        testInvalidDerValue();
    }

    private static void testValidDerValue() throws IOException {
        byte[] octets = {0x01, 0x02, 0x03, 0x04};
        DerValue dv = new DerValue(octets);
        KeyIdentifier ki = new KeyIdentifier(dv);
        byte[] result = ki.getIdentifier();
        assert result.length == octets.length : "Length mismatch";
        for (int i = 0; i < octets.length; i++) {
            assert result[i] == octets[i] : "Byte mismatch at index " + i;
        }
    }

    private static void testInvalidDerValue() {
        try {
            DerValue dv = new DerValue(new byte[]{0x00});
            // Manipulate dv to simulate invalid state if needed
            // For this test, we simulate an invalid DerValue that throws IOException
            DerValue invalidDv = new DerValue() {
                @Override
                public byte[] getOctetString() throws IOException {
                    throw new IOException("Invalid DER value");
                }
            };
            new KeyIdentifier(invalidDv);
            assert false : "Expected IOException was not thrown";
        } catch (IOException e) {
            assert e.getMessage().equals("Invalid DER value") : "Unexpected exception message";
        }
    }
}
