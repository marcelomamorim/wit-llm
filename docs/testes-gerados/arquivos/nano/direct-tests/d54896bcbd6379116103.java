/* @test    @summary Test DerOutputStream.putOID with valid ObjectIdentifier    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutOIDWithValidIdentifier();
        testPutOIDWithEmptyIdentifier();
        testPutOIDWithNullIdentifier();
    }

    static void testPutOIDWithValidIdentifier() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549");
        DerOutputStream dos = new DerOutputStream();
        dos.putOID(oid);
        byte[] result = dos.toByteArray();
        // Basic check: result should contain the encoded OID bytes
        boolean containsOID = false;
        for (byte b : result) {
            if (b != 0) { // simplistic check
                containsOID = true;
                break;
            }
        }
        if (!containsOID) {
            throw new AssertionError("Result does not contain expected OID encoding");
        }
    }

    static void testPutOIDWithEmptyIdentifier() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("");
        DerOutputStream dos = new DerOutputStream();
        try {
            dos.putOID(oid);
            byte[] result = dos.toByteArray();
            if (result.length == 0) {
                throw new AssertionError("Encoding of empty OID should not be empty");
            }
        } catch (IOException e) {
            // Expected if encode method throws for empty string
            // Acceptable, test passes
        }
    }

    static void testPutOIDWithNullIdentifier() {
        ObjectIdentifier oid = null;
        DerOutputStream dos = new DerOutputStream();
        try {
            dos.putOID(oid);
            throw new AssertionError("Expected NullPointerException for null ObjectIdentifier");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            // Unexpected, fail test
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
