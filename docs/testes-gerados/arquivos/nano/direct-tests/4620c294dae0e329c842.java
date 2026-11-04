/*
 * @test
 * @summary Test permits method of DisabledAlgorithmConstraints class
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import sun.security.util.DisabledAlgorithmConstraints;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DisabledAlgorithmConstraintsWitupTest {

    public static void main(String[] args) throws Exception {
        testPermitsReturnsFalseForAnyKey();
    }

    private static void testPermitsReturnsFalseForAnyKey() throws Exception {
        String dummyKeyBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7v7Vn7V7Vn7V7Vn7V7Vn" +
                                "Vn7V7Vn7V7Vn7V7Vn7V7Vn7Vn7V7Vn7Vn7Vn7Vn7Vn7Vn7Vn7Vn7Vn7Vn7Vn7Vn";
        byte[] keyBytes = Base64.getDecoder().decode(dummyKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        Key key = keyFactory.generatePublic(keySpec);
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
        boolean result = dac.permits(key);
        if (result != false) {
            throw new AssertionError("Expected permits to return false for any key, but got true.");
        }
    }
}
