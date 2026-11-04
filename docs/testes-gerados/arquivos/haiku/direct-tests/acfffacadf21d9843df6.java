/*
 * @test
 * @summary Behavioral tests for UUID.fromString(String) using only public API
 * @run main UUIDWitupTest
 */

import java.util.UUID;

public class UUIDWitupTest {

    public static void main(String[] args) {
        testValidLowercaseParsesFields();
        testUppercaseParsesToSameUUID();
        testRoundTripWithConstructor();
        testTooLongStringRejected();
        testMissingDashesRejected();
        testTooManyDashesRejected();
        testEmptyStringRejected();
    }

    static void testValidLowercaseParsesFields() {
        UUID u = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        check(u.getMostSignificantBits() == 0x123e4567e89b12d3L, "msb mismatch");
        check(u.getLeastSignificantBits() == 0xa456426614174000L, "lsb mismatch");
        check(u.toString().equals("123e4567-e89b-12d3-a456-426614174000"), "toString mismatch");
    }

    static void testUppercaseParsesToSameUUID() {
        UUID lower = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID upper = UUID.fromString("123E4567-E89B-12D3-A456-426614174000");
        check(lower.equals(upper), "uppercase input should parse to same UUID");
    }

    static void testRoundTripWithConstructor() {
        UUID original = new UUID(0x123e4567e89b12d3L, 0xa456426614174000L);
        UUID parsed = UUID.fromString(original.toString());
        check(original.equals(parsed), "round trip via toString failed");
        check(parsed.getMostSignificantBits() == original.getMostSignificantBits(), "round trip msb");
        check(parsed.getLeastSignificantBits() == original.getLeastSignificantBits(), "round trip lsb");
    }

    static void testTooLongStringRejected() {
        String longName = "1234567890123456789012345678901234567";
        expectIllegalArgument(longName);
    }

    static void testMissingDashesRejected() {
        expectIllegalArgument("123e4567e89b12d3a456426614174000");
    }

    static void testTooManyDashesRejected() {
        expectIllegalArgument("1-2-3-4-5-6");
    }

    static void testEmptyStringRejected() {
        expectIllegalArgument("");
    }

    static void expectIllegalArgument(String input) {
        try {
            UUID.fromString(input);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for input: " + input);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
