/*
 * @test
 * @summary Verifies Optional.orElseThrow() returns the contained value and throws NoSuchElementException with the expected message when empty
 * @run main OptionalWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalWitupTest {
    public static void main(String[] args) {
        String value = Optional.of("value").orElseThrow();
        if (!"value".equals(value)) {
            throw new AssertionError("Expected 'value' but got: " + value);
        }

        Integer zero = Optional.of(Integer.valueOf(0)).orElseThrow();
        if (zero.intValue() != 0) {
            throw new AssertionError("Expected 0 but got: " + zero);
        }

        boolean thrown = false;
        try {
            Optional.<String>empty().orElseThrow();
        } catch (NoSuchElementException e) {
            thrown = true;
            if (!"No value present".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException for empty Optional");
        }
    }
}
