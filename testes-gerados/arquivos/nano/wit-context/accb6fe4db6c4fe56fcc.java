/* @test    @summary Test for Optional.of(T) method, including null input handling    @run main OptionalWitupTest */

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
            throw new AssertionError("Value mismatch: expected '" + value + "', got '" + result.get() + "'");
        }
    }

    private static void testOfWithNullValue() {
        boolean exceptionThrown = false;
        try {
            Optional.of(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to Optional.of")
        }
    }
}
