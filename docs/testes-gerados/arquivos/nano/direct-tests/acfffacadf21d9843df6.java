/* @test    @summary Test UUID.fromString(String) method for valid and invalid inputs    @run main UUIDWitupTest */

import java.util.UUID;

public class UUIDWitupTest {

    public static void main(String[] args) {
        testValidUUIDString();
        testInvalidUUIDStringTooLong();
        testInvalidUUIDStringMissingDashes();
        testInvalidUUIDStringWrongFormat();
    }

    static void testValidUUIDString() {
        String uuidStr = "123e4567-e89b-12d3-a456-426614174000";
        UUID uuid = UUID.fromString(uuidStr);
        assert uuid != null : "UUID should not be null for valid string";
        String uuidStr2 = uuid.toString();
        assert uuidStr.equals(uuidStr2) : "UUID string representation should match input";
    }

    static void testInvalidUUIDStringTooLong() {
        String invalidStr = "123e4567-e89b-12d3-a456-42661417400000"; // 37 chars
        try {
            UUID.fromString(invalidStr);
            assert false : "Expected IllegalArgumentException for string too long";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("UUID string too large") || e.getMessage().contains("Invalid UUID string") : "Exception message should indicate invalid UUID";
        }
    }

    static void testInvalidUUIDStringMissingDashes() {
        String invalidStr = "123e4567e89b12d3a456426614174000"; // no dashes
        try {
            UUID.fromString(invalidStr);
            assert false : "Expected IllegalArgumentException for missing dashes";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Invalid UUID string") : "Exception message should indicate invalid UUID";
        }
    }

    static void testInvalidUUIDStringWrongFormat() {
        String invalidStr = "123e4567-e89b-12d3-a456-42661417400Z"; // invalid hex
        try {
            UUID.fromString(invalidStr);
            assert false : "Expected NumberFormatException or IllegalArgumentException for invalid hex";
        } catch (NumberFormatException | IllegalArgumentException e) {
            assert e.getMessage().contains("For input string") || e.getMessage().contains("Invalid UUID string") : "Exception message should indicate invalid format";
        }
    }
}
