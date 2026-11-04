/* @test    @summary Test DSAKeyPairGenerator.initialize with valid key sizes    @run main DSAKeyPairGeneratorWitupTest */

import java.security.SecureRandom;
import sun.security.provider.DSAKeyPairGenerator;

public class DSAKeyPairGeneratorWitupTest {

    public static void main(String[] args) {
        testInitializeWithValidSize(1024);
        testInitializeWithValidSize(2048);
        testInitializeWithValidSize(3072);
    }

    private static void testInitializeWithValidSize(int size) {
        DSAKeyPairGenerator generator = new DSAKeyPairGenerator(size);
        try {
            generator.initialize(size, new SecureRandom());
            System.out.println("initialize(" + size + ") succeeded.");
        } catch (Exception e) {
            System.err.println("initialize(" + size + ") failed: " + e);
        }
    }
}
