/*
 * @test
 * @summary Test sun.security.x509.UniqueIdentity constructor with DerInputStream
 * @run main UniqueIdentityWitupTest
 */

import sun.security.x509.UniqueIdentity;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class UniqueIdentityWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerInputStream();
        testDerInputStreamEOF();
    }

    private static void testValidDerInputStream() throws Exception {
        // Construct a minimal valid DER encoding for a BIT STRING with no unused bits and one byte 0xFF
        // DER BIT STRING encoding: tag=0x03, length=0x02, unused bits=0x00, data=0xFF
        byte[] derEncoded = new byte[] { 0x03, 0x02, 0x00, (byte)0xFF };
        DerInputStream derIn = new DerInputStream(derEncoded);
        UniqueIdentity ui = new UniqueIdentity(derIn);
        boolean[] idBits = ui.getId();
        if (idBits == null || idBits.length != 8) {
            throw new RuntimeException("Failed: Expected 8 bits in UniqueIdentity id");
        }
        for (boolean bit : idBits) {
            if (!bit) {
                throw new RuntimeException("Failed: Expected all bits to be true (1)");
            }
        }
        String str = ui.toString();
        if (!str.contains("UniqueIdentity:")) {
            throw new RuntimeException("Failed: toString() does not contain 'UniqueIdentity:'");
        }
    }

    private static void testDerInputStreamEOF() {
        // Provide an empty input stream to cause IOException on getDerValue()
        byte[] empty = new byte[0];
        DerInputStream derIn = new DerInputStream(empty);
        try {
            new UniqueIdentity(derIn);
            throw new RuntimeException("Failed: Expected IOException due to EOF not thrown");
        } catch (IOException e) {
            // Expected exception due to EOF
        }
    }
}
