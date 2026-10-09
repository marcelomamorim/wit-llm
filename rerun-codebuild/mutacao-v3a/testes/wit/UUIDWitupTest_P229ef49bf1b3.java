/* @test    @summary Tests for java.util.UUID class, focusing on timestamp method behavior and exception scenarios    @run main UUIDWitupTest_P229ef49bf1b3 */

import java.util.UUID;

public class UUIDWitupTest_P229ef49bf1b3 {

    public static void main(String[] args) {
        testTimestampWithValidTimeBasedUUID();
        testTimestampWithNonTimeBasedUUID();
    }

    private static void testTimestampWithValidTimeBasedUUID() {
        // Construct a UUID with version() == 1 (time-based UUID)
        // Using a known valid UUID with version 1
        UUID uuid = UUID.fromString("f81d4fae-7dec-11d0-a765-00a0c91e6bf6");
        long timestamp = uuid.timestamp();
        System.out.println("Timestamp for time-based UUID: " + timestamp);
    }

    private static void testTimestampWithNonTimeBasedUUID() {
        // Construct a UUID with version() != 1 to trigger exception
        UUID uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        try {
            long ts = uuid.timestamp();
            System.out.println("Unexpected success, timestamp: " + ts);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}
