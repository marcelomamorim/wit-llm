/*
 * @test
 * @summary Test java.util.UUID.fromString(String) method for valid and invalid inputs
 * @run main java.util.UUIDWitupTest
 */

public class UUIDWitupTest {
    public static void main(String[] args) {
        testValidUUID();
        testUUIDStringTooLarge();
        testUUIDInvalidFormat();
    }

    private static void testValidUUID() {
        // Valid UUID string with correct format and length 36
        String validUUID = "123e4567-e89b-12d3-a456-426655440000";
        java.util.UUID uuid = java.util.UUID.fromString(validUUID);
        // The returned UUID should have the expected string representation
        if (!uuid.toString().equals(validUUID.toLowerCase())) {
            throw new RuntimeException("UUID string mismatch: expected " + validUUID.toLowerCase() + " but got " + uuid.toString());
        }
    }

    private static void testUUIDStringTooLarge() {
        // UUID string longer than 36 characters should throw IllegalArgumentException
        String tooLongUUID = "123e4567-e89b-12d3-a456-426655440000123"; // length > 36
        try {
            java.util.UUID.fromString(tooLongUUID);
            throw new RuntimeException("Expected IllegalArgumentException for UUID string too large");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals("UUID string too large")) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testUUIDInvalidFormat() {
        // UUID string with missing dashes or extra dashes should throw IllegalArgumentException
        String[] invalidUUIDs = {
            "123e4567e89b-12d3-a456-426655440000",  // missing first dash
            "123e4567-e89b12d3-a456-426655440000",  // missing second dash
            "123e4567-e89b-12d3a456-426655440000",  // missing third dash
            "123e4567-e89b-12d3-a456426655440000",  // missing fourth dash
            "123e4567-e89b-12d3-a456-4266-55440000", // extra dash
            "-123e4567-e89b-12d3-a456-426655440000", // leading dash
            "123e4567-e89b-12d3-a456-426655440000-"  // trailing dash
        };

        for (String invalidUUID : invalidUUIDs) {
            try {
                java.util.UUID.fromString(invalidUUID);
                throw new RuntimeException("Expected IllegalArgumentException for invalid UUID format: " + invalidUUID);
            } catch (IllegalArgumentException e) {
                if (!e.getMessage().startsWith("Invalid UUID string")) {
                    throw new RuntimeException("Unexpected exception message for input '" + invalidUUID + "': " + e.getMessage());
                }
            }
        }
    }
}
