/*
 * @test
 * @summary Verify DHKeyPairGenerator.initialize key size validation and acceptance of supported sizes
 * @modules java.base/com.sun.crypto.provider
 * @run main DHKeyPairGeneratorWitupTest
 */

import com.sun.crypto.provider.DHKeyPairGenerator;
import java.security.InvalidParameterException;

public class DHKeyPairGeneratorWitupTest {

    public static void main(String[] args) throws Exception {
        DHKeyPairGenerator gen = new DHKeyPairGenerator();

        gen.initialize(512, null);
        gen.initialize(1024, null);
        gen.initialize(2048, null);

        expectInvalid(0, null);
        expectInvalid(511, null);
        expectInvalid(513, null);
        expectInvalid(1000, null);
        expectInvalid(8256, null);
        expectInvalid(1088, null);
        expectInvalid(-512, null);
    }

    private static void expectInvalid(int keysize, String expectedFragment) {
        DHKeyPairGenerator gen = new DHKeyPairGenerator();
        try {
            gen.initialize(keysize, null);
        } catch (InvalidParameterException e) {
            String msg = e.getMessage();
            if (msg == null || msg.length() == 0) {
                throw new RuntimeException("Empty message for keysize " + keysize);
            }
            if (expectedFragment != null && !msg.contains(expectedFragment)) {
                throw new RuntimeException("Unexpected message for keysize " + keysize + ": " + msg);
            }
            return;
        }
        throw new RuntimeException("Expected InvalidParameterException for keysize " + keysize);
    }
}
