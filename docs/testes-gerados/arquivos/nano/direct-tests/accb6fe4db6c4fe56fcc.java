/* @test    @summary Test for Optional.of(T value) method    @run main OptionalWitupTest */

import java.util.Optional;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testOfWithNonNullValue();
        testOfWithNullValue();
    }

    private static void testOfWithNonNullValue() {
        String value = "test";
        Optional<String> result = Optional.of(value);
        if (!result.isPresent()) {
            throw new AssertionError("Expected value to be present");
        }
        if (!result.get().equals(value)) {
            throw new AssertionError("Expected value: " + value + ", but got: " + result.get());
        }
    }

    private static void testOfWithNullValue() {
        try {
            Optional.of(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
