/*
 * @test
 * @summary Test the encode method of SignerInfo for correct DER encoding.
 * @run main SignerInfoWitupTest
 */

import java.io.IOException;
import sun.security.pkcs.SignerInfo;
import sun.security.util.DerOutputStream;
import sun.security.x509.X500Name;
import sun.security.x509.AlgorithmId;
import java.math.BigInteger;

public class SignerInfoWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeMethod();
    }

    public static void testEncodeMethod() throws IOException {
        // Setup dummy data for SignerInfo
        X500Name issuerName = new X500Name("CN=Test Issuer");
        BigInteger serialNumber = BigInteger.valueOf(123456789);
        AlgorithmId digestAlg = AlgorithmId.get(AlgorithmId.SHA1_oid);
        AlgorithmId encAlg = AlgorithmId.get(AlgorithmId.RSAEncryption_oid);
        byte[] encryptedDigest = new byte[] {0x01, 0x02, 0x03};

        SignerInfo signerInfo = new SignerInfo(issuerName, serialNumber, digestAlg, encAlg, encryptedDigest);

        // Prepare output stream
        DerOutputStream out = new DerOutputStream();
        // Call encode method
        signerInfo.encode(out);
        byte[] encodedBytes = out.toByteArray();

        // Basic validation: check that encoding is not empty and starts with expected tag
        if (encodedBytes == null || encodedBytes.length == 0) {
            throw new AssertionError("Encoded byte array is null or empty");
        }
        // DER tag for sequence is 0x30
        if (encodedBytes[0] != 0x30) {
            throw new AssertionError("Encoded data does not start with SEQUENCE tag");
        }
        System.out.println("encode() method test passed.");
    }
}
