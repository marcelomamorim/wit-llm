/* @test    @summary Test for Cipher.updateAAD(byte[] src) method to ensure it handles null and valid inputs correctly.    @run main CipherWitupTest */

import javax.crypto.Cipher;

public class CipherWitupTest {

    public static void main(String[] args) {
        testUpdateAADWithNull();
        testUpdateAADWithValidData();
    }

    private static void testUpdateAADWithNull() {
        Cipher cipher = createCipherInstance();
        boolean exceptionThrown = false;
        try {
            cipher.updateAAD((byte[]) null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null input");
        }
    }

    private static void testUpdateAADWithValidData() {
        Cipher cipher = createCipherInstance();
        byte[] data = {1, 2, 3, 4, 5};
        try {
            cipher.updateAAD(data);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    private static Cipher createCipherInstance() {
        try {
            return Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Cipher instance", e);
        }
    }
}
