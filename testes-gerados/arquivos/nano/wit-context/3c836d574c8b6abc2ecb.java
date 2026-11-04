/* @test    @summary Test for sun.security.x509.OIDName encoding method    @run main OIDNameWitupTest */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithValidOID();
        testEncodeWithNullOID();
        testEncodeWithInvalidOID();
    }

    private static void testEncodeWithValidOID() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        OIDName oidName = new OIDName(oid);
        DerOutputStream out = new DerOutputStream();
        oidName.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding returned null or empty byte array");
        }
        System.out.println("testEncodeWithValidOID passed");
    }

    private static void testEncodeWithNullOID() {
        try {
            OIDName oidName = new OIDName((ObjectIdentifier) null);
            DerOutputStream out = new DerOutputStream();
            oidName.encode(out);
            throw new AssertionError("Expected NullPointerException or IOException for null OID");
        } catch (NullPointerException | IOException e) {
            System.out.println("testEncodeWithNullOID passed");
        }
    }

    private static void testEncodeWithInvalidOID() {
        try {
            // Creating an ObjectIdentifier with invalid string should throw IOException
            new OIDName("invalid_oid");
            throw new AssertionError("Expected IOException for invalid OID string");
        } catch (IOException e) {
            System.out.println("testEncodeWithInvalidOID passed");
        }
    }
}
