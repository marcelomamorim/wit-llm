/*
 * @test
 * @summary Test sun.security.x509.X509Key encode() method
 * @run main X509KeyWitupTest
 */

import sun.security.x509.X509Key;
import java.security.InvalidKeyException;
import java.util.Arrays;

public class X509KeyWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeReturnsClone();
        testEncodeReturnsNonNull();
    }

    private static void testEncodeReturnsClone() throws Exception {
        X509Key key = new X509Key();

        // Initially encodedKey is null, encode() returns clone of null (should be null)
        byte[] encoded1 = null;
        try {
            encoded1 = key.encode();
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Unexpected InvalidKeyException", e);
        }

        // encoded1 should be null since encodedKey is null
        if (encoded1 != null) {
            throw new RuntimeException("Expected null encoded key initially");
        }

        // Set encodedKey via reflection to a known byte array to test clone
        byte[] testEncoded = new byte[] {1, 2, 3, 4, 5};
        java.lang.reflect.Field encodedKeyField = X509Key.class.getDeclaredField("encodedKey");
        encodedKeyField.setAccessible(true);
        encodedKeyField.set(key, testEncoded);

        byte[] encoded2 = key.encode();

        if (encoded2 == null) {
            throw new RuntimeException("encode() returned null after setting encodedKey");
        }

        if (!Arrays.equals(encoded2, testEncoded)) {
            throw new RuntimeException("encode() returned incorrect byte array");
        }

        // Modify returned array and verify original is not changed (clone test)
        encoded2[0] = 99;
        byte[] encoded3 = (byte[]) encodedKeyField.get(key);
        if (encoded3[0] == 99) {
            throw new RuntimeException("encode() did not return a clone of encodedKey");
        }
    }

    private static void testEncodeReturnsNonNull() throws Exception {
        X509Key key = new X509Key();

        // Set encodedKey to empty array
        byte[] emptyEncoded = new byte[0];
        java.lang.reflect.Field encodedKeyField = X509Key.class.getDeclaredField("encodedKey");
        encodedKeyField.setAccessible(true);
        encodedKeyField.set(key, emptyEncoded);

        byte[] encoded = key.encode();

        if (encoded == null) {
            throw new RuntimeException("encode() returned null for empty encodedKey");
        }

        if (encoded.length != 0) {
            throw new RuntimeException("encode() returned array with unexpected length");
        }
    }
}
