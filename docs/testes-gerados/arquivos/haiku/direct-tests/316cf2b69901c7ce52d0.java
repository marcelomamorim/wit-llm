/*
 * @test
 * @summary Verify that Cipher.updateAAD(byte[]) rejects a null buffer with IllegalArgumentException and the documented message
 * @run main CipherWitupTest
 */

import javax.crypto.Cipher;

public class CipherWitupTest {

    public static void main(String[] args) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        try {
            cipher.updateAAD((byte[]) null);
        } catch (IllegalArgumentException e) {
            if (!"src buffer is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException was not thrown");
    }
}
