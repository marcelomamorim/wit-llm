/*
 * @test
 * @summary Test Optional.get() throws NoSuchElementException when empty and returns value when present
 * @run main OptionalWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testGetOnEmptyOptionalThrows();
        testGetOnPresentOptionalReturnsValue();
        System.out.println("OptionalWitupTest passed");
    }

    private static void testGetOnEmptyOptionalThrows() {
        Optional<Object> empty = Optional.empty();
        try {
            empty.get();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    private static void testGetOnPresentOptionalReturnsValue() {
        String value = "test";
        Optional<String> opt = Optional.of(value);
        String result = opt.get();
        if (!value.equals(result)) {
            throw new AssertionError("Expected value '" + value + "' but got '" + result + "'");
        }
    }
}
