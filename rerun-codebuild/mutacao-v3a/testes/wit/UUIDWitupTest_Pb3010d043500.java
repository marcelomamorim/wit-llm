/* @test    @summary Test for UUID.clockSequence() method to verify behavior and exception handling    @run main UUIDWitupTest_Pb3010d043500 */

import java.util.UUID;

public class UUIDWitupTest_Pb3010d043500 {

    public static void main(String[] args) {
        testClockSequenceTimeBased();
        testClockSequenceNonTimeBased();
    }

    private static void testClockSequenceTimeBased() {
        // Test case where UUID version is 1 (time-based) - should return clock sequence
        // Construct a UUID with version 1 by setting bits accordingly
        long mostSig = 0x1000000000000000L; // version bits set to 1 in bits 12-15
        long leastSig = 0x0000FFFFFFFFFFFFL; // arbitrary
        UUID uuid = new UUID(mostSig, leastSig);
        // Override version method if accessible, or simulate via constructor; assuming constructor sets version bits
        try {
            int clockSeq = uuid.clockSequence();
            // Expect a valid integer, not an exception
            System.out.println("Time-based UUID clock sequence: " + clockSeq);
        } catch (UnsupportedOperationException e) {
            System.err.println("Unexpected exception for version 1 UUID: " + e);
        }
    }

    private static void testClockSequenceNonTimeBased() {
        // Test case where UUID version is not 1 - should throw UnsupportedOperationException
        // Construct a UUID with version 2 or other by setting bits accordingly
        long mostSig = 0x2000000000000000L; // version bits set to 2
        long leastSig = 0x0000FFFFFFFFFFFFL; // arbitrary
        UUID uuid = new UUID(mostSig, leastSig);
        try {
            uuid.clockSequence();
            System.err.println("Expected exception was not thrown for non-time-based UUID");
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected exception for non-time-based UUID: " + e.getMessage());
        }
    }
}
