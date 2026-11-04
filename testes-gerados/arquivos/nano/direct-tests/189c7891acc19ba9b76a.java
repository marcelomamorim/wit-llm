/* @test    @summary Test initialize method of DHKeyPairGenerator for valid key sizes    @run main DHKeyPairGeneratorWitupTest */

import com.sun.crypto.provider.DHKeyPairGenerator;
import java.security.SecureRandom;
import java.security.InvalidParameterException;

public class DHKeyPairGeneratorWitupTest {

    public static void main(String[] args) {
        testInitializeWithValidSizes();
        testInitializeWithInvalidSize();
    }

    static void testInitializeWithValidSizes() {
        int[] validSizes = {512, 1024, 2048, 4096, 8192};
        for (int size : validSizes) {
            try {
                DHKeyPairGenerator generator = new DHKeyPairGenerator();
                generator.initialize(size, new SecureRandom());
                System.out.println("Passed for size: " + size);
            } catch (Exception e) {
                System.err.println("Failed for size: " + size + ", exception: " + e);
            }
        }
    }

    static void testInitializeWithInvalidSize() {
        int[] invalidSizes = {256, 3000, 9000};
        for (int size : invalidSizes) {
            try {
                DHKeyPairGenerator generator = new DHKeyPairGenerator();
                generator.initialize(size, new SecureRandom());
                System.err.println("Unexpected success for invalid size: " + size);
            } catch (InvalidParameterException e) {
                System.out.println("Correctly threw exception for size: " + size);
            } catch (Exception e) {
                System.err.println("Unexpected exception for size: " + size + ", exception: " + e);
            }
        }
    }
}
