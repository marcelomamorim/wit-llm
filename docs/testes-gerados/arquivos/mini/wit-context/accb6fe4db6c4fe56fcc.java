/*
 * @test
 * @summary Test Optional.of(T) throws NullPointerException on null input
 * @run main OptionalWitupTest
 */

import java.util.Optional;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testOfWithNonNull();
        testOfWithNull();
    }

    private static void testOfWithNonNull() {
        String value = "test";
        Optional<String> opt = Optional.of(value);
        if (!opt.isPresent()) {
            throw new AssertionError("Optional.of should contain the non-null value");
        }
        if (!opt.get().equals(value)) {
            throw new AssertionError("Optional.of returned value does not match input");
        }
    }

    private static void testOfWithNull() {
        try {
            Optional.of(null);
            throw new AssertionError("Optional.of(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
