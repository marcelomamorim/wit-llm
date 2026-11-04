/*
 * @test
 * @summary Test sun.security.pkcs.PKCS8Key encode() method behavior
 * @run main PKCS8KeyWitupTest
 */

import sun.security.pkcs.PKCS8Key;
import java.security.InvalidKeyException;
import java.io.IOException;
import sun.security.util.DerOutputStream;

public class PKCS8KeyWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeReturnsClone();
        testEncodeCachesEncodedKey();
    }

    private static void testEncodeReturnsClone() throws Exception {
        PKCS8Key key = new PKCS8Key();

        // Initially encodedKey is null, so encode() triggers encoding
        byte[] encoded1 = key.encode();
        byte[] encoded2 = key.encode();

        // The returned arrays should be equal but not the same instance
        if (encoded1 == encoded2) {
            throw new RuntimeException("encode() returned same byte[] instance on repeated calls");
        }
        if (encoded1.length != encoded2.length) {
            throw new RuntimeException("encode() returned byte[] arrays of different length on repeated calls");
        }
        for (int i = 0; i < encoded1.length; i++) {
            if (encoded1[i] != encoded2[i]) {
                throw new RuntimeException("encode() returned different byte[] content on repeated calls");
            }
        }
    }

    private static void testEncodeCachesEncodedKey() throws Exception {
        PKCS8Key key = new PKCS8Key();

        // Call encode() first time to cache encodedKey
        byte[] firstEncoded = key.encode();

        // Modify the internal encodedKey field via reflection to simulate corruption
        // Reflection is used because encodedKey is protected and no setter is public
        java.lang.reflect.Field encodedKeyField = PKCS8Key.class.getDeclaredField("encodedKey");
        encodedKeyField.setAccessible(true);

        byte[] corrupted = new byte[] {1, 2, 3};
        encodedKeyField.set(key, corrupted);

        // Call encode() again, should return a clone of corrupted array
        byte[] secondEncoded = key.encode();

        if (secondEncoded.length != corrupted.length) {
            throw new RuntimeException("encode() did not return cached encodedKey clone after modification");
        }
        for (int i = 0; i < corrupted.length; i++) {
            if (secondEncoded[i] != corrupted[i]) {
                throw new RuntimeException("encode() returned incorrect cached encodedKey clone content");
            }
        }

        // Ensure returned array is a clone, not the same instance
        if (secondEncoded == corrupted) {
            throw new RuntimeException("encode() returned the actual cached encodedKey array, not a clone");
        }
    }
}
