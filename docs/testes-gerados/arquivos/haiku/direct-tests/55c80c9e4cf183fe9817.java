/*
 * @test
 * @summary Verify CertificateIssuerName.encode writes the DER form of the wrapped X500Name
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import sun.security.util.DerOutputStream;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;

public class CertificateIssuerNameWitupTest {

    public static void main(String[] args) throws Exception {
        encodeMatchesX500NameDer();
        encodeStartsWithSequenceTag();
        encodeRoundTripKeepsName();
        encodeWithNullNameThrowsNpe();
    }

    static void encodeMatchesX500NameDer() throws Exception {
        X500Name name = new X500Name("CN=Test, O=Example, C=US");
        CertificateIssuerName cin = new CertificateIssuerName(name);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        cin.encode(bos);

        DerOutputStream expected = new DerOutputStream();
        name.encode(expected);
        byte[] exp = expected.toByteArray();

        if (!Arrays.equals(exp, bos.toByteArray())) {
            throw new RuntimeException("encoded bytes differ from X500Name DER");
        }
    }

    static void encodeStartsWithSequenceTag() throws Exception {
        X500Name name = new X500Name("CN=Another, C=DE");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        new CertificateIssuerName(name).encode(bos);
        byte[] out = bos.toByteArray();
        if (out.length == 0 || out[0] != (byte) 0x30) {
            throw new RuntimeException("encoding does not start with DER SEQUENCE tag");
        }
    }

    static void encodeRoundTripKeepsName() throws Exception {
        X500Name name = new X500Name("CN=RoundTrip, O=Example, C=US");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        new CertificateIssuerName(name).encode(bos);

        CertificateIssuerName decoded =
            new CertificateIssuerName(new ByteArrayInputStream(bos.toByteArray()));
        if (!name.toString().equals(decoded.toString())) {
            throw new RuntimeException("round trip changed the name: " + decoded.toString());
        }
    }

    static void encodeWithNullNameThrowsNpe() throws Exception {
        CertificateIssuerName cin = new CertificateIssuerName((X500Name) null);
        try {
            cin.encode(new ByteArrayOutputStream());
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name");
    }
}
