/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putOID with valid and null ObjectIdentifier
 * @run main sun.security.util.DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testPutOIDWithValidOID();
        testPutOIDWithNullOID();
    }

    static void testPutOIDWithValidOID() throws IOException {
        DerOutputStream derOut = new DerOutputStream();
        // Create a simple ObjectIdentifier with a known OID string
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        derOut.putOID(oid);
        byte[] encoded = derOut.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded OID output is empty");
        }
    }

    static void testPutOIDWithNullOID() {
        DerOutputStream derOut = new DerOutputStream();
        try {
            derOut.putOID(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
