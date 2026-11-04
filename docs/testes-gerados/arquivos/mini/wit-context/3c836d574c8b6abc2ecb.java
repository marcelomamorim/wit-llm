/*
 * @test
 * @summary Test sun.security.x509.OIDName.encode(DerOutputStream) method
 * @run main OIDNameWitupTest
 */

import sun.security.x509.OIDName;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class OIDNameWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeValidOID();
        testEncodeWithEmptyOID();
    }

    private static void testEncodeValidOID() throws IOException {
        // Create OIDName with a valid OID string
        OIDName oidName = new OIDName("1.2.3.4");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        oidName.encode(derOut);
        byte[] encoded = baos.toByteArray();

        // The encoded output should not be empty
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for valid OID");
        }
    }

    private static void testEncodeWithEmptyOID() throws IOException {
        // Create an ObjectIdentifier with minimal valid OID "0.0"
        ObjectIdentifier oid = new ObjectIdentifier("0.0");
        OIDName oidName = new OIDName(oid);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        oidName.encode(derOut);
        byte[] encoded = baos.toByteArray();

        // The encoded output should not be empty
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for minimal OID");
        }
    }
}
