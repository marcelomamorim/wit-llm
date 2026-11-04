/* @test    @summary Test PBEWithSHA1AndRC4_128 constructor for correct instantiation and behavior    @run main PKCS12PBECipherCoreWitupTest */

import com.sun.crypto.provider.PKCS12PBECipherCore;
import java.security.NoSuchAlgorithmException;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) {
        testConstructorCreatesInstance();
    }

    static void testConstructorCreatesInstance() {
        try {
            PKCS12PBECipherCore instance = new PKCS12PBECipherCore.PBEWithSHA1AndRC4_128();
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
