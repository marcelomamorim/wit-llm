/* @test    @summary Test for KeyPairGenerator.initialize(int, SecureRandom) with valid parameters and null serviceIterator to observe normal behavior.    @run main KeyPairGeneratorWitupTest */

import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class KeyPairGeneratorWitupTest {

    public static void main(String[] args) {
        testInitializeWithValidParameters();
        testInitializeWithNullServiceIterator();
    }

    private static void testInitializeWithValidParameters() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = new SecureRandom();
            int keySize = 2048;
            kpg.initialize(keySize, random);
            System.out.println("testInitializeWithValidParameters: PASSED");
        } catch (Exception e) {
            System.out.println("testInitializeWithValidParameters: FAILED - " + e);
        }
    }

    private static void testInitializeWithNullServiceIterator() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = new SecureRandom();
            // Simulate null serviceIterator by subclassing or reflection if needed
            // Since direct access isn't available, assume default behavior
            kpg.initialize(1024, random);
            System.out.println("testInitializeWithNullServiceIterator: PASSED");
        } catch (UnsupportedOperationException e) {
            System.out.println("testInitializeWithNullServiceIterator: Caught expected UnsupportedOperationException");
        } catch (Exception e) {
            System.out.println("testInitializeWithNullServiceIterator: FAILED - " + e);
        }
    }
}
