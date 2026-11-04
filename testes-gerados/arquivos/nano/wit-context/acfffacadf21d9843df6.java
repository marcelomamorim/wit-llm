/*
 * @test
 * @summary Tests for java.util.UUID.fromString(String)
 * @run main UUIDWitupTest
 */

import java.util.UUID;

public class UUIDWitupTest {

    public static void main(String[] args) {
        testValidUUIDString();
        testUUIDStringTooLarge();
        testInvalidUUIDFormat();
    }

    private static void testValidUUIDString() {
        String validUUID = "123e4567-e89b-12d3-a456-426614174000";
        UUID uuid = UUID.fromString(validUUID);
        if (uuid == null) {
            throw new AssertionError("Expected non-null UUID for valid string");
        }
    }

    private static void testUUIDStringTooLarge() {
        String largeUUID = "123e4567-e89b-12d3-a456-42661417400000"; // length > 36
        try {
            UUID.fromString(largeUUID);
            throw new AssertionError("Expected IllegalArgumentException for UUID string too large");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testInvalidUUIDFormat() {
        String invalidUUID = "123e4567-e89b-12d3-a456-42661417400X"; // invalid hex
        try {
            UUID.fromString(invalidUUID);
            throw new AssertionError("Expected NumberFormatException for invalid hex in UUID string");
        } catch (NumberFormatException e) {
            // Expected exception
        } catch (IllegalArgumentException e) {
            // Also acceptable if thrown
        }
    }
}
