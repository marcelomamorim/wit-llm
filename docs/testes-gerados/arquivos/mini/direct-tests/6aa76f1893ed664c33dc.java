/*
 * @test
 * @summary Test EncryptedPrivateKeyInfo constructor with AlgorithmId and encryptedData
 * @run main EncryptedPrivateKeyInfoWitupTest
 */

import sun.security.pkcs.EncryptedPrivateKeyInfo;
import sun.security.x509.AlgorithmId;

public class EncryptedPrivateKeyInfoWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy AlgorithmId for testing
        AlgorithmId algid = AlgorithmId.get("SHA1");
        if (algid == null) {
            throw new RuntimeException("AlgorithmId.get returned null for SHA1");
        }

        // Prepare encrypted data
        byte[] encryptedData = new byte[] {1, 2, 3, 4, 5};

        // Construct EncryptedPrivateKeyInfo
        EncryptedPrivateKeyInfo epki = new EncryptedPrivateKeyInfo(algid, encryptedData);

        // Verify getAlgorithm returns the same AlgorithmId
        AlgorithmId returnedAlgid = epki.getAlgorithm();
        if (!returnedAlgid.equals(algid)) {
            throw new RuntimeException("AlgorithmId returned by getAlgorithm does not match the one passed to constructor");
        }

        // Verify getEncryptedData returns a clone equal to original
        byte[] returnedData = epki.getEncryptedData();
        if (returnedData == encryptedData) {
            throw new RuntimeException("getEncryptedData returned the same array instance, expected a clone");
        }
        if (returnedData.length != encryptedData.length) {
            throw new RuntimeException("Returned encrypted data length mismatch");
        }
        for (int i = 0; i < encryptedData.length; i++) {
            if (returnedData[i] != encryptedData[i]) {
                throw new RuntimeException("Returned encrypted data content mismatch at index " + i);
            }
        }

        // Modify returnedData and verify original encryptedData is not affected
        returnedData[0] = (byte)(returnedData[0] + 1);
        if (encryptedData[0] == returnedData[0]) {
            throw new RuntimeException("Original encryptedData was modified when changing returnedData clone");
        }

        System.out.println("Test passed.");
    }
}
