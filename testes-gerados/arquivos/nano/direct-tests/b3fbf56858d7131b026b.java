/* @test    @summary Test DESedeKeySpec constructor with valid and invalid key lengths    @run main DESedeKeySpecWitupTest */

import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;

public class DESedeKeySpecWitupTest {

    public static void main(String[] args) {
        testValidKey();
        testNullKey();
        testShortKey();
    }

    static void testValidKey() {
        byte[] validKey = new byte[24];
        try {
            DESedeKeySpec spec = new DESedeKeySpec(validKey);
            byte[] returnedKey = spec.getKey();
            assert returnedKey != null : "Returned key should not be null";
            assert returnedKey.length == 24 : "Returned key length should be 24";
        } catch (InvalidKeyException e) {
            throw new AssertionError("Valid key should not throw exception", e);
        }
    }

    static void testNullKey() {
        try {
            new DESedeKeySpec((byte[]) null);
            throw new AssertionError("Null key should throw NullPointerException or InvalidKeyException");
        } catch (NullPointerException | InvalidKeyException e) {
            // Expected exception
        }
    }

    static void testShortKey() {
        byte[] shortKey = new byte[23];
        try {
            new DESedeKeySpec(shortKey);
            throw new AssertionError("Short key should throw InvalidKeyException");
        } catch (InvalidKeyException e) {
            // Expected exception
        }
    }
}
