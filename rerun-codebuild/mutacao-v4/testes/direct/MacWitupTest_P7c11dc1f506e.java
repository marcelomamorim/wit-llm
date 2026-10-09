/* @test
   @summary Verifies that calling update(ByteBuffer) on Mac throws IllegalStateException if not initialized.
   @run main MacWitupTest_P7c11dc1f506e
 */
import java.util.*;
import java.security.*;
import javax.crypto.*;
import java.nio.ByteBuffer;
public class MacWitupTest_P7c11dc1f506e {
    public static void main(String[] args) throws Exception {
        // Obtain a Mac instance for a known algorithm, e.g., HmacSHA256
        Mac mac = Mac.getInstance("HmacSHA256");
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3});
        // Expect IllegalStateException because MAC is not initialized
        try {
            mac.update(buffer);
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
