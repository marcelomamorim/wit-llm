/* @test    @summary Test for UUID.timestamp() method to verify behavior and exception handling    @run main UUIDWitupTest_Pb28324c7ae30 */

import java.util.UUID;

public class UUIDWitupTest_Pb28324c7ae30 {

    public static void main(String[] args) {
        testTimestampWithVersion1();
        testTimestampWithNonVersion1();
    }

    private static void testTimestampWithVersion1() {
        // Create a UUID with version 1 (time-based)
        long mostSigBits = 0x1000000000000000L; // version bits set to 1 in bits 12-15
        long leastSigBits = 0L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        long timestamp = uuid.timestamp();
        System.out.println("Timestamp for version 1 UUID: " + timestamp);
        // Expect no exception
    }

    private static void testTimestampWithNonVersion1() {
        // Create a UUID with version 2 (not time-based)
        long mostSigBits = 0x2000000000000000L; // version bits set to 2
        long leastSigBits = 0L;
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        try {
            uuid.timestamp();
            System.out.println("Error: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}
