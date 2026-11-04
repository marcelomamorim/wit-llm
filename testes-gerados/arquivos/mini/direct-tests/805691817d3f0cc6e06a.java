/*
 * @test
 * @summary Test sun.security.x509.X500Name encode(DerOutputStream) method
 * @run main X500NameWitupTest
 */

import sun.security.x509.X500Name;
import sun.security.x509.RDN;
import sun.security.x509.AVA;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeEmptyRDNs();
        testEncodeSingleRDN();
        testEncodeMultipleRDNs();
    }

    private static void testEncodeEmptyRDNs() throws IOException {
        // Create X500Name with empty RDN array
        RDN[] emptyRDNs = new RDN[0];
        X500Name name = new X500Name(emptyRDNs);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream out = new DerOutputStream(baos);

        name.encode(out);

        byte[] encoded = baos.toByteArray();

        // The encoding should be a DER SEQUENCE with zero length content
        // DER SEQUENCE tag is 0x30
        if (encoded.length != 2 || encoded[0] != DerValue.tag_Sequence || encoded[1] != 0) {
            throw new RuntimeException("Encoding of empty X500Name is incorrect");
        }
    }

    private static void testEncodeSingleRDN() throws IOException {
        // Create a single AVA (AttributeTypeAndValue) with commonName=Test
        AVA ava = new AVA(AVA.commonName_oid, "Test");
        RDN rdn = new RDN(new AVA[] { ava });
        X500Name name = new X500Name(new RDN[] { rdn });

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream out = new DerOutputStream(baos);

        name.encode(out);

        byte[] encoded = baos.toByteArray();

        // The encoding should start with DER SEQUENCE tag
        if (encoded.length < 4 || encoded[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("Encoding of single RDN X500Name is incorrect");
        }
    }

    private static void testEncodeMultipleRDNs() throws IOException {
        // Create multiple AVAs and RDNs
        AVA ava1 = new AVA(AVA.commonName_oid, "Test1");
        AVA ava2 = new AVA(AVA.organizationName_oid, "Org");
        RDN rdn1 = new RDN(new AVA[] { ava1 });
        RDN rdn2 = new RDN(new AVA[] { ava2 });
        X500Name name = new X500Name(new RDN[] { rdn1, rdn2 });

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream out = new DerOutputStream(baos);

        name.encode(out);

        byte[] encoded = baos.toByteArray();

        // The encoding should start with DER SEQUENCE tag
        if (encoded.length < 6 || encoded[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("Encoding of multiple RDNs X500Name is incorrect");
        }
    }
}
