/*
 * @test
 * @summary Test for Optional.get() method to ensure it throws NoSuchElementException when no value is present and returns the value when present.
 * @run main OptionalWitupTest
 */

import java.util.Optional;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testGetWithValue();
        testGetWithoutValue();
    }

    static void testGetWithValue() {
        Optional<String> opt = Optional.of("test");
        String result = opt.get();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', but got: " + result);
        }
    }

    static void testGetWithoutValue() {
        Optional<String> opt = Optional.empty();
        try {
            opt.get();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (java.util.NoSuchElementException e) {
            // expected
        }
    }
}
