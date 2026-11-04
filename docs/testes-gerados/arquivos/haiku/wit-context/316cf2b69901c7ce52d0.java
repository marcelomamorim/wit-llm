/*
 * @test
 * @summary Verify Cipher.updateAAD(byte[]) rejects a null buffer with IllegalArgumentException
 * @run main CipherWitupTest
 */

import javax.crypto.Cipher;

public class CipherWitupTest {
    public static void main(String[] args) throws Exception {
        Cipher c = Cipher.getInstance("AES");
        try {
            c.updateAAD((byte[]) null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException expected) {
        }
    }
}
