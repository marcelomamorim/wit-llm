/*
 * @test
 * @summary Witup-derived checks for Optional.orElseThrow(): returns the present value and throws NoSuchElementException with message "No value present" when empty
 * @run main OptionalWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalWitupTest {
    public static void main(String[] args) {
        Optional<String> present = Optional.of("value");
        if (!"value".equals(present.orElseThrow())) {
            throw new RuntimeException("orElseThrow() did not return the present value");
        }

        Optional<Integer> zero = Optional.of(0);
        if (zero.orElseThrow().intValue() != 0) {
            throw new RuntimeException("orElseThrow() did not return a present falsy value");
        }

        Optional<String> empty = Optional.empty();
        try {
            empty.orElseThrow();
            throw new RuntimeException("Expected NoSuchElementException for empty Optional");
        } catch (NoSuchElementException e) {
            if (!"No value present".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
