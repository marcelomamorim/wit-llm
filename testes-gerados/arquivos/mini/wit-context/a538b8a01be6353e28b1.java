/*
 * @test
 * @summary Test sun.security.provider.DSAKeyPairGenerator.initialize(int, SecureRandom) for valid and invalid key sizes
 * @run main DSAKeyPairGeneratorWitupTest
 */

import java.security.InvalidParameterException;
import java.security.SecureRandom;
import sun.security.provider.DSAKeyPairGenerator;

public class DSAKeyPairGeneratorWitupTest {
    public static void main(String[] args) throws Exception {
        testValidKeySizes();
        testInvalidKeySizesBelow512();
        testInvalidKeySizesNotMultipleOf64();
        testInvalidKeySizesUnsupportedLarge();
    }

    private static void testValidKeySizes() {
        int[] validSizes = {512, 576, 640, 704, 768, 832, 896, 960, 1024, 2048, 3072};
        SecureRandom random = new SecureRandom();
        for (int size : validSizes) {
            try {
                DSAKeyPairGenerator gen = new DSAKeyPairGenerator(1024); // default constructor with dummy size
                gen.initialize(size, random);
            } catch (InvalidParameterException e) {
                throw new RuntimeException("Unexpected InvalidParameterException for valid size: " + size, e);
            }
        }
    }

    private static void testInvalidKeySizesBelow512() {
        SecureRandom random = new SecureRandom();
        int[] invalidSizes = {0, 1, 511, 100};
        for (int size : invalidSizes) {
            try {
                DSAKeyPairGenerator gen = new DSAKeyPairGenerator(1024);
                gen.initialize(size, random);
                throw new RuntimeException("Expected InvalidParameterException not thrown for size: " + size);
            } catch (InvalidParameterException expected) {
                // expected
            }
        }
    }

    private static void testInvalidKeySizesNotMultipleOf64() {
        SecureRandom random = new SecureRandom();
        int[] invalidSizes = {513, 570, 1023};
        for (int size : invalidSizes) {
            try {
                DSAKeyPairGenerator gen = new DSAKeyPairGenerator(1024);
                gen.initialize(size, random);
                throw new RuntimeException("Expected InvalidParameterException not thrown for size: " + size);
            } catch (InvalidParameterException expected) {
                // expected
            }
        }
    }

    private static void testInvalidKeySizesUnsupportedLarge() {
        SecureRandom random = new SecureRandom();
        int[] invalidSizes = {1025, 1500, 4096};
        for (int size : invalidSizes) {
            try {
                DSAKeyPairGenerator gen = new DSAKeyPairGenerator(1024);
                gen.initialize(size, random);
                throw new RuntimeException("Expected InvalidParameterException not thrown for size: " + size);
            } catch (InvalidParameterException expected) {
                // expected
            }
        }
    }
}
