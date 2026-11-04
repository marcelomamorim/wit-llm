/*
 * @test
 * @summary Tests for sun.security.provider.DSAKeyPairGenerator.initialize(int, SecureRandom)
 * @run main DSAKeyPairGeneratorWitupTest
 */

import java.security.SecureRandom;
import sun.security.provider.DSAKeyPairGenerator;

public class DSAKeyPairGeneratorWitupTest {

    public static void main(String[] args) {
        testInitializeWithInvalidStrength();
        testInitializeWithInvalidSize();
    }

    private static void testInitializeWithInvalidStrength() {
        DSAKeyPairGenerator generator = new DSAKeyPairGenerator(1024);
        try {
            // modlen less than 512
            generator.initialize(511, new SecureRandom());
            System.out.println("Failed: Expected InvalidParameterException for modlen=511");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for modlen=511");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for modlen=511: " + e);
        }
        try {
            // modlen between 512 and 1024 but not multiple of 64
            generator.initialize(1025, new SecureRandom());
            System.out.println("Failed: Expected InvalidParameterException for modlen=1025");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for modlen=1025");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for modlen=1025: " + e);
        }
    }

    private static void testInitializeWithInvalidSize() {
        DSAKeyPairGenerator generator = new DSAKeyPairGenerator(1024);
        try {
            // modlen > 1024 and not 2048 or 3072
            generator.initialize(4096, new SecureRandom());
            System.out.println("Failed: Expected InvalidParameterException for modlen=4096");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for modlen=4096");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for modlen=4096: " + e);
        }
    }
}
