/*
 * @test
 * @summary Checks public observable behavior of the EncryptedPrivateKeyInfo(AlgorithmId, byte[]) constructor
 * @modules java.base/sun.security.pkcs
 * @run main EncryptedPrivateKeyInfoWitupTest
 */

import java.util.Arrays;
import sun.security.pkcs.EncryptedPrivateKeyInfo;

public class EncryptedPrivateKeyInfoWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] data = {1, 2, 3, 4};
        byte[] original = data.clone();

        EncryptedPrivateKeyInfo info = new EncryptedPrivateKeyInfo(null, data);

        if (info.getAlgorithm() != null) {
            throw new RuntimeException("getAlgorithm should return the stored null algid");
        }

        data[0] = 9;
        if (!Arrays.equals(info.getEncryptedData(), original)) {
            throw new RuntimeException("constructor must copy encryptedData defensively");
        }

        byte[] out = info.getEncryptedData();
        out[1] = 9;
        if (!Arrays.equals(info.getEncryptedData(), original)) {
            throw new RuntimeException("getEncryptedData must return a copy");
        }

        try {
            new EncryptedPrivateKeyInfo(null, null);
            throw new RuntimeException("null encryptedData should be rejected");
        } catch (NullPointerException expected) {
            // current implementation fails on encryptedData.clone()
        }
    }
}
