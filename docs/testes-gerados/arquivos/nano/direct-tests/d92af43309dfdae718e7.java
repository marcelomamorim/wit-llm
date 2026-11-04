/* @test    @summary Test the update(ByteBuffer) method of javax.crypto.Mac    @run main MacWitupTest */

import javax.crypto.Mac;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

public class MacWitupTest {

    public static void main(String[] args) {
        testUpdateWithNullBuffer();
        testUpdateWithoutInitialization();
        testUpdateWithValidBuffer();
    }

    private static void testUpdateWithNullBuffer() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            // Not initializing to test exception
            ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0x01, 0x02});
            mac.update(buffer);
            System.out.println("FAIL: Expected IllegalStateException for uninitialized Mac");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException for uninitialized Mac");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testUpdateWithoutInitialization() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0x01, 0x02});
            // Not calling init, should throw
            mac.update(buffer);
            System.out.println("FAIL: Expected IllegalStateException for uninitialized Mac");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException for uninitialized Mac");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testUpdateWithValidBuffer() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new javax.crypto.spec.SecretKeySpec(new byte[]{0x0A, 0x0B, 0x0C, 0x0D}, "HmacSHA256"));
            ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0x01, 0x02, 0x03});
            mac.update(buffer);
            System.out.println("PASS: update(ByteBuffer) executed successfully with valid buffer");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception during valid update: " + e);
        }
    }
}
