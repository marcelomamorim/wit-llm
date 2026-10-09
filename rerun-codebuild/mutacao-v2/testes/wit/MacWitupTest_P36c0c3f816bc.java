/*
 * @test
 * @summary Deterministic tests for javax.crypto.Mac.update(ByteBuffer) method
 * @run main MacWitupTest_P36c0c3f816bc
 */

import javax.crypto.Mac;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;

public class MacWitupTest_P36c0c3f816bc {

    public static void main(String[] args) throws Exception {
        testUpdateNullBuffer();
        testUpdateWithoutInit();
        testUpdateWithValidInput();
    }

    private static void testUpdateNullBuffer() throws Exception {
        Mac mac = getMacInstance();
        mac.init(new javax.crypto.spec.SecretKeySpec(new byte[16], mac.getAlgorithm()));
        try {
            mac.update((ByteBuffer) null);
            throw new RuntimeException("Expected IllegalArgumentException for null ByteBuffer input");
        } catch (IllegalArgumentException iae) {
            // Expected
        }
    }

    private static void testUpdateWithoutInit() throws Exception {
        Mac mac = getMacInstance();
        try {
            mac.update(ByteBuffer.allocate(0));
            throw new RuntimeException("Expected IllegalStateException when update called before init");
        } catch (IllegalStateException ise) {
            // Expected
        }
    }

    private static void testUpdateWithValidInput() throws Exception {
        Mac mac = getMacInstance();
        mac.init(new javax.crypto.spec.SecretKeySpec(new byte[16], mac.getAlgorithm()));
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3, 4});
        mac.update(buffer);
        // No exception expected, verify that buffer position advanced by 4
        if (buffer.position() != 4) {
            throw new RuntimeException("Buffer position not advanced correctly after update");
        }
    }

    private static Mac getMacInstance() throws NoSuchAlgorithmException, NoSuchProviderException {
        // Use a standard algorithm guaranteed to be present
        // Use default provider to avoid provider issues
        return Mac.getInstance("HmacSHA256");
    }
}
