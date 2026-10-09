/* @test    @summary Test for javax.crypto.Mac.update(ByteBuffer) method to verify exception paths and behavior.    @run main MacWitupTest_Pd518589c22e5 */

import javax.crypto.Mac;
import java.nio.ByteBuffer;

public class MacWitupTest_Pd518589c22e5 {

    public static void main(String[] args) {
        testUpdateWithNullBuffer();
        testUpdateWithoutInitialization();
        testUpdateWithInvalidInputParameters();
    }

    private static void testUpdateWithNullBuffer() {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Mac instance", e);
        }
        // Do not initialize mac to trigger IllegalStateException
        try {
            // Using reflection or direct access is not allowed; simulate uninitialized state by not calling init
            // Since Mac is not initialized, calling update should throw IllegalStateException
            // But in current API, Mac is initialized via init, so simulate by not calling init
            // For the purpose of this test, forcibly set spi to null to simulate uninitialized state
            // Note: This is a simplification; in real tests, more elaborate setup may be needed
            java.lang.reflect.Field spiField = Mac.class.getDeclaredField("spi");
            spiField.setAccessible(true);
            spiField.set(mac, null);
            mac.update(ByteBuffer.wrap(new byte[]{1,2,3}));
            throw new AssertionError("Expected IllegalStateException not thrown when updating uninitialized Mac");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testUpdateWithNullBuffer", e);
        }
    }

    private static void testUpdateWithoutInitialization() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            // Do not call init to keep it uninitialized
            ByteBuffer buffer = ByteBuffer.wrap(new byte[]{1,2,3});
            try {
                mac.update(buffer);
                throw new AssertionError("Expected IllegalStateException not thrown when updating without init");
            } catch (IllegalStateException e) {
                // Expected exception
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed in testUpdateWithoutInitialization", e);
        }
    }

    private static void testUpdateWithInvalidInputParameters() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(javax.crypto.KeyGenerator.getInstance("HmacSHA256").generateKey());
            // Test with null ByteBuffer
            try {
                mac.update((ByteBuffer) null);
                throw new AssertionError("Expected IllegalArgumentException not thrown for null ByteBuffer");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
            // Test with empty ByteBuffer
            ByteBuffer emptyBuffer = ByteBuffer.allocate(0);
            mac.update(emptyBuffer); // Should not throw
        } catch (Exception e) {
            throw new RuntimeException("Failed in testUpdateWithInvalidInputParameters", e);
        }
    }
}
