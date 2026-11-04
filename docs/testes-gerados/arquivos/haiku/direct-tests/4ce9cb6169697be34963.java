/*
 * @test
 * @summary Verify SecureRandom.generateSeed argument validation and returned seed length
 * @run main SecureRandomWitupTest
 */

import java.security.SecureRandom;

public class SecureRandomWitupTest {
    public static void main(String[] args) throws Exception {
        SecureRandom sr = new SecureRandom();

        boolean thrown = false;
        try {
            sr.generateSeed(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for negative numBytes");
        }

        byte[] empty = sr.generateSeed(0);
        if (empty == null || empty.length != 0) {
            throw new RuntimeException("generateSeed(0) must return an empty array");
        }

        int[] sizes = {1, 16, 64};
        for (int n : sizes) {
            byte[] seed = sr.generateSeed(n);
            if (seed == null || seed.length != n) {
                throw new RuntimeException("unexpected seed length for " + n);
            }
        }
    }
}
