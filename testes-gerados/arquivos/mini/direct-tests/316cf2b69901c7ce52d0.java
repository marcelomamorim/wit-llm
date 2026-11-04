/*
 * @test
 * @summary Test javax.crypto.Cipher.updateAAD(byte[] src) method
 * @run main CipherWitupTest
 */

import javax.crypto.Cipher;

public class CipherWitupTest {
    public static void main(String[] args) throws Exception {
        testUpdateAADWithNull();
        testUpdateAADWithEmptyArray();
        testUpdateAADWithValidArray();
    }

    private static void testUpdateAADWithNull() {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.updateAAD(null);
            throw new RuntimeException("Expected IllegalArgumentException for null src");
        } catch (IllegalArgumentException e) {
            if (!"src buffer is null".equals(e.getMessage())) {
                throw new RuntimeException("IllegalArgumentException message mismatch", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private static void testUpdateAADWithEmptyArray() throws Exception {
        Cipher c = Cipher.getInstance("AES");
        // Should not throw any exception
        c.updateAAD(new byte[0]);
    }

    private static void testUpdateAADWithValidArray() throws Exception {
        Cipher c = Cipher.getInstance("AES");
        byte[] aad = {1, 2, 3, 4, 5};
        // Should not throw any exception
        c.updateAAD(aad);
    }
}
