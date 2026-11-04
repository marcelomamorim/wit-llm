/*
 * @test
 * @summary Test Optional.of(T) with non-null and null values
 * @run main java.util.OptionalWitupTest
 */

public class OptionalWitupTest {
    public static void main(String[] args) {
        testOfWithNonNull();
        testOfWithNull();
    }

    private static void testOfWithNonNull() {
        String value = "test";
        java.util.Optional<String> opt = java.util.Optional.of(value);
        if (!opt.isPresent()) {
            throw new AssertionError("Optional.of() should contain a value when non-null is passed");
        }
        if (!opt.get().equals(value)) {
            throw new AssertionError("Optional.of() returned Optional with incorrect value");
        }
    }

    private static void testOfWithNull() {
        try {
            java.util.Optional.of(null);
            throw new AssertionError("Optional.of(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
