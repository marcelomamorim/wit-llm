/*
 * @test
 * @summary Behavioral tests for Mac.update(ByteBuffer): state check, null argument check, and equivalence with byte[] update
 * @run main MacWitupTest
 */

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MacWitupTest {

    private static final byte[] KEY_BYTES = new byte[] {
        0x0b, 0x0b, 0x0b, 0x0b, 0x0b, 0x0b, 0x0b, 0x0b,
        0x0b, 0x0b, 0x0b, 0x0b, 0x0b, 0x0b, 0x0b, 0x0b
    };

    public static void main(String[] args) throws Exception {
        testUpdateByteBufferBeforeInitThrowsIllegalState();
        testUpdateNullByteBufferAfterInitThrowsIllegalArgument();
        testUpdateByteBufferMatchesByteArrayUpdate();
    }

    private static Mac newInitializedMac() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(KEY_BYTES, "HmacSHA256"));
        return mac;
    }

    static void testUpdateByteBufferBeforeInitThrowsIllegalState() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        boolean thrown = false;
        try {
            mac.update(ByteBuffer.wrap(new byte[] {1, 2, 3}));
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException for uninitialized Mac");
        }
    }

    static void testUpdateNullByteBufferAfterInitThrowsIllegalArgument() throws Exception {
        Mac mac = newInitializedMac();
        boolean thrown = false;
        try {
            mac.update((ByteBuffer) null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for null ByteBuffer");
        }
    }

    static void testUpdateByteBufferMatchesByteArrayUpdate() throws Exception {
        byte[] data = new byte[] {'a', 'b', 'c', 'd', 'e', 'f', 'g'};

        Mac viaBuffer = newInitializedMac();
        viaBuffer.update(ByteBuffer.wrap(data));
        byte[] resultBuffer = viaBuffer.doFinal();

        Mac viaArray = newInitializedMac();
        viaArray.update(data);
        byte[] resultArray = viaArray.doFinal();

        if (!Arrays.equals(resultBuffer, resultArray)) {
            throw new RuntimeException("update(ByteBuffer) result differs from update(byte[])");
        }
    }
}
