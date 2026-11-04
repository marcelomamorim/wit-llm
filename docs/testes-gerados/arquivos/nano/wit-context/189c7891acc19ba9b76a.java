/* @test    @summary Test initialize method of DHKeyPairGenerator for valid and invalid key sizes    @run main DHKeyPairGeneratorWitupTest */

import com.sun.crypto.provider.DHKeyPairGenerator;
import java.security.SecureRandom;

public class DHKeyPairGeneratorWitupTest {

    public static void main(String[] args) {
        testInitializeValidSizes();
        testInitializeInvalidSizes();
    }

    private static void testInitializeValidSizes() {
        int[] validSizes = {512, 576, 640, 704, 768, 832, 896, 960, 1024, 2048, 4096, 8192};
        for (int size : validSizes) {
            try {
                DHKeyPairGenerator generator = new DHKeyPairGenerator();
                generator.initialize(size, new SecureRandom());
                System.out.println("Passed valid size: " + size);
            } catch (Exception e) {
                System.err.println("Failed valid size: " + size + " with exception: " + e);
            }
        }
    }

    private static void testInitializeInvalidSizes() {
        int[] invalidSizes = {511, 513, 1023, 1025, 8191, 8193};
        for (int size : invalidSizes) {
            try {
                DHKeyPairGenerator generator = new DHKeyPairGenerator();
                generator.initialize(size, new SecureRandom());
                System.err.println("Failed to throw exception for invalid size: " + size);
            } catch (IllegalArgumentException | InvalidParameterException e) {
                System.out.println("Correctly threw exception for size: " + size);
            } catch (Exception e) {
                System.err.println("Unexpected exception for size: " + size + ": " + e);
            }
        }
    }
}
