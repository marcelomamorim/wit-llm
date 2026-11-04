/*
 * @test
 * @summary Witup-generated behavioral tests for UUID.fromString
 * @run main UUIDWitupTest
 */

public class UUIDWitupTest {

    public static void main(String[] args) {
        String valid = "123e4567-e89b-12d3-a456-426614174000";
        UUID u = UUID.fromString(valid);
        check(valid.equals(u.toString()), "round trip of valid string");
        check(u.getMostSignificantBits() == 0x123e4567e89b12d3L, "most significant bits");
        check(u.getLeastSignificantBits() == 0xa456426614174000L, "least significant bits");
        check(u.equals(new UUID(0x123e4567e89b12d3L, 0xa456426614174000L)), "equals to constructed UUID");

        String upper = "123E4567-E89B-12D3-A456-426614174000";
        check(UUID.fromString(upper).equals(u), "uppercase hex accepted");

        UUID r = new UUID(0x0123456789abcdefL, 0xfedcba9876543210L);
        check(UUID.fromString(r.toString()).equals(r), "round trip of arbitrary UUID");

        expectIAE("123e4567-e89b-12d3-a456-4266141740000");
        expectIAE("123e4567e89b12d3a456426614174000");
        expectIAE("");
        expectIAE("1-2-3-4-5-6");
        expectIAE("g23e4567-e89b-12d3-a456-426614174000");
        expectNPE(null);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    private static void expectIAE(String input) {
        try {
            UUID.fromString(input);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for input: " + input);
    }

    private static void expectNPE(String input) {
        try {
            UUID.fromString(input);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null input");
    }
}
