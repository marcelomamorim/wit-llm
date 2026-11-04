/*
 * @test
 * @summary Test sun.security.pkcs.SignerInfo.encode(DerOutputStream) for basic functionality and OutOfMemoryError edge cases
 * @run main SignerInfoWitupTest
 */

import sun.security.pkcs.SignerInfo;
import sun.security.util.DerOutputStream;
import sun.security.x509.AlgorithmId;
import sun.security.x509.X500Name;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class SignerInfoWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeBasic();
        testEncodeWithLargeEncryptedDigest();
    }

    private static void testEncodeBasic() throws IOException {
        // Prepare minimal valid inputs
        X500Name issuerName = new X500Name("CN=TestIssuer");
        BigInteger serial = BigInteger.valueOf(123456789L);
        AlgorithmId digestAlg = AlgorithmId.get("SHA-256");
        AlgorithmId digestEncAlg = AlgorithmId.get("RSA");
        byte[] encryptedDigest = new byte[] {1, 2, 3, 4, 5};

        SignerInfo signerInfo = new SignerInfo(issuerName, serial, digestAlg, digestEncAlg, encryptedDigest);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        signerInfo.encode(derOut);

        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded output is null or empty");
        }
    }

    private static void testEncodeWithLargeEncryptedDigest() {
        // Attempt to provoke OutOfMemoryError by using a very large encryptedDigest array
        // This test tries to cover the expaths suggesting OutOfMemoryError in encode
        try {
            X500Name issuerName = new X500Name("CN=TestIssuer");
            BigInteger serial = BigInteger.ONE;
            AlgorithmId digestAlg = AlgorithmId.get("SHA-256");
            AlgorithmId digestEncAlg = AlgorithmId.get("RSA");

            // Allocate a large byte array close to Integer.MAX_VALUE to provoke memory issues
            // Use a smaller size to avoid crashing the test environment
            int largeSize = 100_000_000; // 100 million bytes (~100MB), large but not too large
            byte[] largeEncryptedDigest = new byte[largeSize];

            SignerInfo signerInfo = new SignerInfo(issuerName, serial, digestAlg, digestEncAlg, largeEncryptedDigest);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DerOutputStream derOut = new DerOutputStream(baos);

            signerInfo.encode(derOut);

            // If encode succeeds, test passes silently
        } catch (OutOfMemoryError e) {
            // Expected possible error path from WIT expaths
            // Test passes if OutOfMemoryError is thrown
        } catch (IOException e) {
            // IOException is allowed by method signature, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
