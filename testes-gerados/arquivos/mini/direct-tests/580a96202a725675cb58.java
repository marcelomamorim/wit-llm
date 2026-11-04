/*
 * @test
 * @summary Test sun.security.x509.X500Name constructor with byte[] input
 * @run main sun.security.x509.X500NameWitupTest
 */

import sun.security.x509.X500Name;
import sun.security.util.DerValue;
import java.io.IOException;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerEncodedName();
        testInvalidDerEncodedName();
    }

    private static void testValidDerEncodedName() throws IOException {
        // Construct a minimal valid DER encoding for an X500Name
        // Using DerValue to create a valid encoding for RDNSequence with one RDN
        // RDNSequence ::= SEQUENCE OF RelativeDistinguishedName
        // RelativeDistinguishedName ::= SET OF AttributeTypeAndValue
        // AttributeTypeAndValue ::= SEQUENCE { type OBJECT IDENTIFIER, value ANY }

        // OID for commonName: 2.5.4.3
        byte[] oidCommonName = {0x55, 0x04, 0x03};
        // PrintableString "Test"
        byte[] valuePrintableString = {0x13, 0x04, 'T', 'e', 's', 't'};

        // AttributeTypeAndValue SEQUENCE
        byte[] atv = new byte[2 + oidCommonName.length + valuePrintableString.length];
        int pos = 0;
        atv[pos++] = 0x30; // SEQUENCE tag
        atv[pos++] = (byte)(oidCommonName.length + valuePrintableString.length);
        System.arraycopy(oidCommonName, 0, atv, pos, oidCommonName.length);
        pos += oidCommonName.length;
        System.arraycopy(valuePrintableString, 0, atv, pos, valuePrintableString.length);

        // RelativeDistinguishedName SET
        byte[] rdn = new byte[2 + atv.length];
        rdn[0] = 0x31; // SET tag
        rdn[1] = (byte)atv.length;
        System.arraycopy(atv, 0, rdn, 2, atv.length);

        // RDNSequence SEQUENCE
        byte[] rdnSequence = new byte[2 + rdn.length];
        rdnSequence[0] = 0x30; // SEQUENCE tag
        rdnSequence[1] = (byte)rdn.length;
        System.arraycopy(rdn, 0, rdnSequence, 2, rdn.length);

        // Now create X500Name with this valid DER encoding
        X500Name name = new X500Name(rdnSequence);
        if (name == null) {
            throw new RuntimeException("X500Name constructor returned null for valid input");
        }
    }

    private static void testInvalidDerEncodedName() {
        // Provide invalid DER encoding (empty array) to cause IOException
        byte[] invalid = new byte[0];
        try {
            new X500Name(invalid);
            throw new RuntimeException("Expected IOException not thrown for invalid input");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
