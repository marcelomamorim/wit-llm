/* @test
   @summary Verifies the behavior of UUID.timestamp() method.
   @run main UUIDWitupTest_P5c57daf8c638
 */
import java.util.UUID;
public class UUIDWitupTest_P5c57daf8c638 {
    public static void main(String[] args) {
        // Test case: UUID with version 1 should return a timestamp
        long mostSigBits = 0x0000000000000001L;
        long leastSigBits = 0x0000000000000000L;
        UUID uuidV1 = new UUID(mostSigBits, leastSigBits) {
            @Override
            public int version() {
                return 1;
            }
        };
        long timestamp = uuidV1.timestamp();
        if (timestamp != ((mostSigBits & 0x0FFFL) << 48 | ((mostSigBits >>> 16) & 0x0FFFFL) << 32 | (mostSigBits >>> 32))) {
            throw new AssertionError("Timestamp does not match expected value");
        }
        // Test case: UUID with version not 1 should throw UnsupportedOperationException
        UUID uuidNonV1 = new UUID(mostSigBits, leastSigBits) {
            @Override
            public int version() {
                return 2;
            }
        };
        try {
            uuidNonV1.timestamp();
            throw new AssertionError("Expected UnsupportedOperationException for non-version 1 UUID");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
