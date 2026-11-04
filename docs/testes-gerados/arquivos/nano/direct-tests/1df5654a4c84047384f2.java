/* @test    @summary Test for PBEWithSHA1AndRC4_40 constructor behavior    @run main PKCS12PBECipherCoreWitupTest */

import java.security.NoSuchAlgorithmException;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) {
        testConstructorCreatesInstance();
    }

    public static void testConstructorCreatesInstance() {
        try {
            com.sun.crypto.provider.PKCS12PBECipherCore instance = new com.sun.crypto.provider.PKCS12PBECipherCore.PBEWithSHA1AndRC4_40();
            if (instance == null) {
                throw new AssertionError("Instance should not be null");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Constructor threw NoSuchAlgorithmException", e);
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception", t);
        }
    }
}
