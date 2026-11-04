/*
 * @test
 * @summary Test sun.security.x509.X500Name.encode(DerOutputStream) for normal and edge cases
 * @run main X500NameWitupTest
 */

import sun.security.x509.X500Name;
import sun.security.x509.RDN;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidX500Name();
        testEncodeWithEmptyRDNArray();
    }

    private static void testEncodeWithValidX500Name() throws IOException {
        // Construct X500Name with common fields
        X500Name name = new X500Name("CN=Test, OU=Dev, O=OpenJDK, C=US");
        DerOutputStream out = new DerOutputStream();
        name.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeWithEmptyRDNArray() throws IOException {
        // Construct X500Name with empty RDN array
        RDN[] emptyRDNs = new RDN[0];
        X500Name emptyName = new X500Name(emptyRDNs);
        DerOutputStream out = new DerOutputStream();
        emptyName.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output for empty RDN array is empty");
        }
    }
}
