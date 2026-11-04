/*
 * @test
 * @summary Test sun.security.pkcs.SignerInfo encode method
 * @run main SignerInfoWitupTest
 */

import sun.security.pkcs.SignerInfo;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X500Name;
import sun.security.util.DerOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class SignerInfoWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeBasic();
        testEncodeWithNulls();
    }

    private static void testEncodeBasic() throws IOException {
        X500Name issuer = new X500Name("CN=TestIssuer");
        BigInteger serial = BigInteger.valueOf(123456789L);
        AlgorithmId digestAlg = AlgorithmId.get("SHA-256");
        AlgorithmId digestEncAlg = AlgorithmId.get("RSA");
        byte[] encryptedDigest = new byte[] {1, 2, 3, 4, 5};

        SignerInfo si = new SignerInfo(issuer, serial, digestAlg, digestEncAlg, encryptedDigest);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        si.encode(derOut);

        byte[] encoded = baos.toByteArray();
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeWithNulls() throws IOException {
        // Test with null encryptedDigest to check no NPE or failure
        X500Name issuer = new X500Name("CN=NullTest");
        BigInteger serial = BigInteger.ONE;
        AlgorithmId digestAlg = AlgorithmId.get("SHA-1");
        AlgorithmId digestEncAlg = AlgorithmId.get("DSA");
        byte[] encryptedDigest = null;

        SignerInfo si = new SignerInfo(issuer, serial, digestAlg, digestEncAlg, encryptedDigest);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        si.encode(derOut);

        byte[] encoded = baos.toByteArray();
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when encryptedDigest is null");
        }
    }
}
