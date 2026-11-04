/*
 * @test
 * @summary EncryptedPrivateKeyInfo(AlgorithmId, byte[]) keeps the algorithm reference, copies input and output arrays, and rejects null data
 * @modules java.base/sun.security.pkcs java.base/sun.security.x509
 * @run main EncryptedPrivateKeyInfoWitupTest
 */

import sun.security.pkcs.EncryptedPrivateKeyInfo;
import sun.security.x509.AlgorithmId;

public class EncryptedPrivateKeyInfoWitupTest {

    public static void main(String[] args) throws Exception {
        testNullAlgorithmIsStored();
        testInputArrayIsCopied();
        testReturnedArrayIsCopied();
        testEmptyEncryptedDataAccepted();
        testNullEncryptedDataThrowsNpe();
        System.out.println("PASSED");
    }

    private static void testNullAlgorithmIsStored() throws Exception {
        byte[] data = {1, 2, 3, 4};
        EncryptedPrivateKeyInfo info =
            new EncryptedPrivateKeyInfo((AlgorithmId) null, data);
        if (info.getAlgorithm() != null) {
            throw new RuntimeException("expected null algorithm to be returned");
        }
        byte[] out = info.getEncryptedData();
        if (!java.util.Arrays.equals(out, data)) {
            throw new RuntimeException("encrypted data mismatch");
        }
    }

    private static void testInputArrayIsCopied() throws Exception {
        byte[] data = {10, 20, 30};
        EncryptedPrivateKeyInfo info =
            new EncryptedPrivateKeyInfo((AlgorithmId) null, data);
        data[0] = 99;
        byte[] out = info.getEncryptedData();
        if (out[0] != 10) {
            throw new RuntimeException("constructor did not clone input array");
        }
    }

    private static void testReturnedArrayIsCopied() throws Exception {
        byte[] data = {5, 6, 7};
        EncryptedPrivateKeyInfo info =
            new EncryptedPrivateKeyInfo((AlgorithmId) null, data);
        byte[] first = info.getEncryptedData();
        first[1] = 42;
        byte[] second = info.getEncryptedData();
        if (second[1] != 6) {
            throw new RuntimeException("getEncryptedData did not return a clone");
        }
    }

    private static void testEmptyEncryptedDataAccepted() throws Exception {
        EncryptedPrivateKeyInfo info =
            new EncryptedPrivateKeyInfo((AlgorithmId) null, new byte[0]);
        if (info.getEncryptedData().length != 0) {
            throw new RuntimeException("expected empty encrypted data");
        }
    }

    private static void testNullEncryptedDataThrowsNpe() {
        try {
            new EncryptedPrivateKeyInfo((AlgorithmId) null, null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null encryptedData");
    }
}
