/* @test    @summary Test for UUID.timestamp() method - only valid for version 1 UUIDs    @run main UUIDWitupTest_P229ef49bf1b3 */

import java.util.UUID;

public class UUIDWitupTest_P229ef49bf1b3 {

    public static void main(String[] args) {
        testTimestampWithVersion1();
        testTimestampWithNonVersion1();
    }

    static void testTimestampWithVersion1() {
        long mostSigBits = 0x0001000000000000L; // version 1 indicator in bits 12-15
        long leastSigBits = 0L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        try {
            long ts = uuid.timestamp();
            System.out.println("timestamp() for version 1 UUID: " + ts);
        } catch (UnsupportedOperationException e) {
            System.err.println("Unexpected exception for version 1 UUID: " + e);
        }
    }

    static void testTimestampWithNonVersion1() {
        // create UUID with version != 1, e.g., version 4 (random UUID)
        long mostSigBits = 0x4000000000000000L; // version 4 indicator in bits 12-15
        long leastSigBits = 0L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        try {
            uuid.timestamp();
            System.err.println("Expected exception not thrown for non-version 1 UUID");
        } catch (UnsupportedOperationException e) {
            System.out.println("Correctly caught exception for non-version 1 UUID: " + e);
        }
    }
}
