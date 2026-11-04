/* @test    @summary Test for KeyStore.SecretKeyEntry constructor with null input    @run main KeyStoreWitupTest */

import java.security.KeyStore;
import javax.crypto.SecretKey;
import java.util.Collections;

public class KeyStoreWitupTest {

    public static void main(String[] args) {
        testSecretKeyEntryNull();
    }

    public static void testSecretKeyEntryNull() {
        try {
            new KeyStore.SecretKeyEntry(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            String message = e.getMessage();
            if (message != null && message.contains("invalid null input")) {
                System.out.println("Passed: NullPointerException thrown as expected");
            } else {
                System.err.println("Failed: NullPointerException thrown but message mismatch");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
