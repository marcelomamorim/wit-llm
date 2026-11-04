/*
 * @test
 * @summary Test CipherInputStream.read() method for basic functionality.
 * @run main CipherInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

public class CipherInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadReturnsData();
        testReadEndOfStream();
    }

    static void testReadReturnsData() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Cipher cipher = Cipher.getInstance("AES");
        // Initialize cipher for decryption with dummy key (not used in read)
        cipher.init(Cipher.DECRYPT_MODE, new javax.crypto.spec.SecretKeySpec(new byte[16], "AES"));
        CipherInputStream cis = new CipherInputStream(bais, cipher);
        int totalRead = 0;
        int byteRead;
        while ((byteRead = cis.read()) != -1) {
            totalRead++;
        }
        // Since cipher is not actually initialized with real key, data is unchanged
        // Expect totalRead to equal data length
        if (totalRead != data.length) {
            throw new AssertionError("Expected to read " + data.length + " bytes, but read " + totalRead);
        }
    }

    static void testReadEndOfStream() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new javax.crypto.spec.SecretKeySpec(new byte[16], "AES"));
        CipherInputStream cis = new CipherInputStream(bais, cipher);
        int result = cis.read();
        if (result != -1) {
            throw new AssertionError("Expected -1 at end of stream, but got " + result);
        }
    }
}
