/*
 * @test
 * @summary Deterministic tests for EncryptedPrivateKeyInfo(AlgorithmId, byte[]) constructor
 * @run main EncryptedPrivateKeyInfoWitupTest
 */

import sun.security.pkcs.EncryptedPrivateKeyInfo;
import sun.security.x509.AlgorithmId;

public class EncryptedPrivateKeyInfoWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorClonesEncryptedData();
        testConstructorStoresAlgorithmId();
        testConstructorWithEmptyEncryptedData();
    }

    private static void testConstructorClonesEncryptedData() {
        byte[] original = new byte[] {1, 2, 3, 4};
        AlgorithmId algid = AlgorithmId.get("SHA1");
        EncryptedPrivateKeyInfo epki = new EncryptedPrivateKeyInfo(algid, original);

        byte[] returned = epki.getEncryptedData();

        // Modify original array after construction
        original[0] = 99;

        // The returned encryptedData should not reflect changes to original
        if (returned[0] != 1) {
            throw new RuntimeException("Encrypted data was not cloned defensively");
        }
    }

    private static void testConstructorStoresAlgorithmId() {
        AlgorithmId algid = AlgorithmId.get("SHA1");
        byte[] data = new byte[] {10, 20, 30};
        EncryptedPrivateKeyInfo epki = new EncryptedPrivateKeyInfo(algid, data);

        AlgorithmId returnedAlg = epki.getAlgorithm();

        if (returnedAlg == null) {
            throw new RuntimeException("AlgorithmId returned is null");
        }

        if (!returnedAlg.equals(algid)) {
            throw new RuntimeException("AlgorithmId returned does not match the one passed in constructor");
        }
    }

    private static void testConstructorWithEmptyEncryptedData() {
        AlgorithmId algid = AlgorithmId.get("SHA1");
        byte[] emptyData = new byte[0];
        EncryptedPrivateKeyInfo epki = new EncryptedPrivateKeyInfo(algid, emptyData);

        byte[] returned = epki.getEncryptedData();

        if (returned == null) {
            throw new RuntimeException("Encrypted data returned is null for empty input");
        }

        if (returned.length != 0) {
            throw new RuntimeException("Encrypted data length mismatch for empty input");
        }
    }
}
