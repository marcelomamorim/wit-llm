/* @test    @summary Test for KeyStore.SecretKeyEntry constructor handling null inputs    @run main KeyStoreWitupTest */

import java.security.KeyStore;
import javax.crypto.SecretKey;
import java.util.HashSet;
import java.util.Collections;
import java.util.Set;

public class KeyStoreWitupTest {

    public static void main(String[] args) {
        testSecretKeyEntryNullSecretKey();
        testSecretKeyEntryNullAttributes();
        testSecretKeyEntryValidInputs();
    }

    public static void testSecretKeyEntryNullSecretKey() {
        try {
            Set<KeyStore.Entry.Attribute> attrs = new HashSet<>();
            new KeyStore.SecretKeyEntry(null, attrs);
            System.out.println("FAIL: Expected NullPointerException for null secretKey");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null secretKey");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    public static void testSecretKeyEntryNullAttributes() {
        try {
            SecretKey key = new DummySecretKey();
            new KeyStore.SecretKeyEntry(key, null);
            System.out.println("FAIL: Expected NullPointerException for null attributes");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null attributes");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    public static void testSecretKeyEntryValidInputs() {
        try {
            SecretKey key = new DummySecretKey();
            Set<KeyStore.Entry.Attribute> attrs = new HashSet<>();
            KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key, attrs);
            if (entry != null) {
                System.out.println("PASS: Successfully created SecretKeyEntry with valid inputs");
            } else {
                System.out.println("FAIL: SecretKeyEntry is null with valid inputs");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static class DummySecretKey implements SecretKey {
        public String getAlgorithm() { return "AES"; }
        public String getFormat() { return "RAW"; }
        public byte[] getEncoded() { return new byte[0]; }
    }
}
