/*
 * @test
 * @summary Test DHKeyPairGenerator constructor behavior with default key size
 * @run main DHKeyPairGeneratorWitupTest
 */

import java.security.InvalidParameterException;
import com.sun.crypto.provider.DHKeyPairGenerator;

public class DHKeyPairGeneratorWitupTest {
    public static void main(String[] args) {
        // The constructor calls initialize(DEF_DH_KEY_SIZE, null)
        // We test that the constructor does not throw for the default key size
        // and that it throws if DEF_DH_KEY_SIZE is invalid (simulated by reflection is not allowed, so we test only normal case).

        // Normal case: should not throw
        try {
            DHKeyPairGenerator gen = new DHKeyPairGenerator();
        } catch (InvalidParameterException e) {
            throw new RuntimeException("Constructor threw InvalidParameterException unexpectedly", e);
        }

        // We cannot change DEF_DH_KEY_SIZE as it is a constant, so we cannot test the exception paths directly.
        // The expaths from WIT indicate exceptions if DEF_DH_KEY_SIZE is out of range or params null and keysize > 1024.
        // These cannot be triggered here without reflection or modifying constants, which is disallowed.

        System.out.println("Test passed: DHKeyPairGenerator constructor behaves as expected with default parameters.");
    }
}
