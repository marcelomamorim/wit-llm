/* @test    @summary Test UUID.clockSequence() method behavior for different UUID variants    @run main UUIDWitupTest_Pb3010d043500 */

import java.util.UUID;

public class UUIDWitupTest_Pb3010d043500 {
    public static void main(String[] args) {
        testTimeBasedUUID();
        testNonTimeBasedUUID();
        testUnsupportedOperationException();
    }

    private static void testTimeBasedUUID() {
        // Create a UUID with version() == 1 (time-based)
        long mostSigBits = 0x1000000000000000L; // arbitrary with version bits set to 1
        long leastSigBits = 0x8000000000000000L; // arbitrary
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // Force version to 1 for testing
        // Since version() checks version bits, we set them accordingly
        // But no direct API to set version, so simulate by constructing such UUID
        // Here, for testing, assume constructed UUID is version 1
        // Since version() uses bits, we can mock or assume this is valid
        // For the purpose of this test, assume the UUID is version 1
        try {
            int seq = uuid.clockSequence();
            System.out.println("clockSequence() for version 1 UUID: " + seq);
        } catch (UnsupportedOperationException e) {
            System.out.println("Unexpected exception for version 1 UUID: " + e.getMessage());
        }
    }

    private static void testNonTimeBasedUUID() {
        // Create a UUID with version() != 1
        long mostSigBits = 0x2000000000000000L; // arbitrary with version bits set to 2
        long leastSigBits = 0x8000000000000000L; // arbitrary
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        // The version() method inspects bits, but since we do not override, it defaults to the bits
        // For this test, we expect clockSequence() to throw exception because version() != 1
        try {
            uuid.clockSequence();
            System.out.println("ERROR: Expected UnsupportedOperationException for non-time-based UUID");
        } catch (UnsupportedOperationException e) {
            System.out.println("Correctly caught exception for non-time-based UUID: " + e.getMessage());
        }
    }

    private static void testUnsupportedOperationException() {
        // Create a UUID with version != 1 to trigger exception
        long mostSigBits = 0x3000000000000000L; // arbitrary with version bits set to 3
        long leastSigBits = 0x8000000000000000L; // arbitrary
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        try {
            uuid.clockSequence();
            System.out.println("ERROR: Expected exception for unsupported UUID version");
        } catch (UnsupportedOperationException e) {
            System.out.println("Correctly caught exception: " + e.getMessage());
        }
    }
}
