/*
 * @test
 * @summary Test UniqueIdentity constructor with DerInputStream
 * @run main UniqueIdentityWitupTest
 */

import java.io.IOException;
import sun.security.x509.UniqueIdentity;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class UniqueIdentityWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerInputStream();
        testDerInputStreamWithInvalidData();
    }

    private static void testValidDerInputStream() throws IOException {
        // Prepare a DerValue with a valid unaligned bit string
        byte[] bitStringEncoded = new byte[] {
            0x03, 0x03, 0x05, (byte)0xA0, 0x00
        };
        DerInputStream derIn = new DerInputStream(bitStringEncoded);
        UniqueIdentity uid = new UniqueIdentity(derIn);
        boolean[] idBits = uid.getId();
        if (idBits == null || idBits.length == 0) {
            throw new RuntimeException("Expected non-empty id bits");
        }
    }

    private static void testDerInputStreamWithInvalidData() {
        // Provide invalid DER data to cause IOException
        byte[] invalidData = new byte[] {0x01, 0x01, 0x00}; // Not a BIT STRING tag
        DerInputStream derIn = new DerInputStream(invalidData);
        try {
            new UniqueIdentity(derIn);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
