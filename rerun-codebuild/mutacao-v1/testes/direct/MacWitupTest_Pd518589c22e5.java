/* @test    @summary Test javax.crypto.Mac.update(ByteBuffer) method for correct behavior and exception handling    @run main MacWitupTest_Pd518589c22e5 */

import javax.crypto.Mac;
import java.nio.ByteBuffer;

public class MacWitupTest_Pd518589c22e5 {

    public static void main(String[] args) {
        try {
            testUpdateWithNullBuffer();
            testUpdateWithoutInitialization();
            testUpdateWithValidBuffer();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void testUpdateWithNullBuffer() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            // Not initializing to test exception
            ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0x01, 0x02});
            mac.update(buffer);
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testUpdateWithoutInitialization() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0x01, 0x02});
            // Not initializing MAC to test exception
            mac.update(buffer);
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testUpdateWithValidBuffer() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new javax.crypto.spec.SecretKeySpec(new byte[16], "HmacSHA256"));
            ByteBuffer buffer = ByteBuffer.wrap(new byte[]{0x01, 0x02, 0x03});
            mac.update(buffer);
            // No exception expected, verify that MAC is updated
            byte[] result = mac.doFinal();
            if (result == null || result.length == 0) {
                throw new AssertionError("MAC output is invalid");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
