/*
 * @test
 * @summary Test javax.crypto.Cipher.updateAAD(byte[]) for null argument and normal usage
 * @run main CipherWitupTest
 */

import javax.crypto.Cipher;

public class CipherWitupTest {
    public static void main(String[] args) throws Exception {
        testUpdateAADNull();
        testUpdateAADNormal();
        System.out.println("PASS");
    }

    private static void testUpdateAADNull() {
        Cipher c = Cipher.getInstance("AES");
        try {
            c.updateAAD(null);
            throw new RuntimeException("Expected IllegalArgumentException for null src");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testUpdateAADNormal() throws Exception {
        Cipher c = Cipher.getInstance("AES");
        byte[] data = new byte[] {1, 2, 3, 4};
        // Should not throw and should accept the input
        c.updateAAD(data);
    }
}
