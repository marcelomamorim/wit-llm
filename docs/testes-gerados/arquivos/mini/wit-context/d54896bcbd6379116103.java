/*
 * @test
 * @summary Test putOID method of DerOutputStream for correct encoding and exception safety
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testPutOIDValidEncoding();
        testPutOIDNullThrows();
    }

    private static void testPutOIDValidEncoding() throws IOException {
        // Create a DerOutputStream with default size
        DerOutputStream derOut = new DerOutputStream();

        // Create a simple ObjectIdentifier with a valid OID string
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549");

        // Use putOID to encode the OID into the DerOutputStream
        derOut.putOID(oid);

        // The output buffer should contain some bytes after encoding
        if (derOut.size() == 0) {
            throw new AssertionError("DerOutputStream buffer is empty after putOID encoding");
        }

        // Additional check: the first byte should be the OID tag (0x06)
        byte[] encoded = derOut.toByteArray();
        if (encoded[0] != 0x06) {
            throw new AssertionError("Encoded data does not start with OID tag 0x06");
        }
    }

    private static void testPutOIDNullThrows() {
        DerOutputStream derOut = new DerOutputStream();
        try {
            derOut.putOID(null);
            throw new AssertionError("Expected NullPointerException when passing null to putOID");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            // IOException is declared but not expected for null input
            throw new AssertionError("Unexpected IOException when passing null to putOID", e);
        }
    }
}
