/*
 * @test
 * @summary Test java.util.UUID.fromString(String) for valid and invalid inputs
 * @run main UUIDWitupTest
 */

import java.util.UUID;

public class UUIDWitupTest {
    public static void main(String[] args) {
        testValidUUIDString();
        testUUIDStringTooLong();
        testUUIDStringInvalidDashPositions();
        System.out.println("All tests passed.");
    }

    private static void testValidUUIDString() {
        // Valid UUID string example (version 4 UUID)
        String validUUID = "123e4567-e89b-12d3-a456-426655440000";
        UUID uuid = UUID.fromString(validUUID);
        // Check that the returned UUID's string form equals the input ignoring case
        if (!uuid.toString().equalsIgnoreCase(validUUID)) {
            throw new AssertionError("UUID string conversion mismatch");
        }
    }

    private static void testUUIDStringTooLong() {
        // Input string longer than 36 characters should throw IllegalArgumentException
        String tooLong = "123e4567-e89b-12d3-a456-426655440000X"; // length 37
        try {
            UUID.fromString(tooLong);
            throw new AssertionError("Expected IllegalArgumentException for too long string");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testUUIDStringInvalidDashPositions() {
        // Strings with invalid dash positions should throw IllegalArgumentException
        // Case 1: missing dashes (dash4 < 0)
        String missingDashes = "123e4567e89b12d3a456426655440000"; // no dashes
        try {
            UUID.fromString(missingDashes);
            throw new AssertionError("Expected IllegalArgumentException for missing dashes");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Case 2: extra dash (dash5 >= 0)
        String extraDash = "123e4567-e89b-12d3-a456-42665544-0000"; // extra dash
        try {
            UUID.fromString(extraDash);
            throw new AssertionError("Expected IllegalArgumentException for extra dash");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
