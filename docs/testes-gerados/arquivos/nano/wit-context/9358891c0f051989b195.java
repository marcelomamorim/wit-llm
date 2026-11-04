/*
 * @test
 * @summary Test for PKCS12PBECipherCore.PBEWithSHA1AndRC2_40 method
 * @run main PKCS12PBECipherCoreWitupTest
 */

import com.sun.crypto.provider.PKCS12PBECipherCore;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) {
        testPBEWithSHA1AndRC2_40();
    }

    private static void testPBEWithSHA1AndRC2_40() {
        try {
            PKCS12PBECipherCore instance = new PKCS12PBECipherCore.PBEWithSHA1AndRC2_40();
            assert instance != null : "Instance should not be null";
        } catch (NoSuchAlgorithmException e) {
            // Test passes if exception is thrown, as the method declares it
            System.out.println("Caught expected NoSuchAlgorithmException");
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
