/*
 * @test
 * @summary Verifies that DHKeyPairGenerator.initialize rejects invalid key sizes with InvalidParameterException
 * @modules java.base/com.sun.crypto.provider
 * @run main DHKeyPairGeneratorWitupTest
 */

import java.security.InvalidParameterException;
import com.sun.crypto.provider.DHKeyPairGenerator;

public class DHKeyPairGeneratorWitupTest {

    public static void main(String[] args) throws Exception {
        DHKeyPairGenerator kpg = new DHKeyPairGenerator();

        int[] invalidSizes = {511, 520, 8256, 1088};
        for (int size : invalidSizes) {
            try {
                kpg.initialize(size, null);
                throw new RuntimeException("No InvalidParameterException for keysize " + size);
            } catch (InvalidParameterException expected) {
                // expected
            }
        }

        kpg.initialize(512, null);
        kpg.initialize(1024, null);
    }
}
