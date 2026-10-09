/*
 * @test
 * @summary Test javax.crypto.Mac update(ByteBuffer) method behavior
 * @run main MacWitupTest_P36c0c3f816bc
 */

import javax.crypto.Mac;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.Provider;
import java.security.Provider.Service;
import java.util.Iterator;

public class MacWitupTest_P36c0c3f816bc {

    public static void main(String[] args) throws Exception {
        testUpdateNullBufferThrows();
        testUpdateWithoutInitThrows();
        testUpdateWithValidInput();
    }

    // Test that update(null) throws IllegalArgumentException
    private static void testUpdateNullBufferThrows() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new javax.crypto.spec.SecretKeySpec(new byte[16], "HmacSHA256"));
        try {
            mac.update((ByteBuffer) null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    // Test that update(ByteBuffer) throws IllegalStateException if Mac not initialized
    private static void testUpdateWithoutInitThrows() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        // Do not call init
        try {
            mac.update(ByteBuffer.allocate(0));
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    // Test that update(ByteBuffer) with valid input does not throw and updates internal state
    private static void testUpdateWithValidInput() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = new byte[16];
        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (byte) i;
        }
        javax.crypto.spec.SecretKeySpec key = new javax.crypto.spec.SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(key);

        byte[] data = new byte[32];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i * 2);
        }
        ByteBuffer buffer = ByteBuffer.wrap(data);

        mac.update(buffer);

        // After update, the buffer position should be advanced to limit
        if (buffer.position() != buffer.limit()) {
            throw new RuntimeException("Buffer position not advanced after update");
        }

        // Call doFinal and check output length matches Mac length
        byte[] result = mac.doFinal();
        if (result == null || result.length != mac.getMacLength()) {
            throw new RuntimeException("doFinal result invalid");
        }
    }
}
