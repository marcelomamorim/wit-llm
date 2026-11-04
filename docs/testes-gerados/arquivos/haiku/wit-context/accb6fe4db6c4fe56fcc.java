/*
 * @test
 * @summary Witup-derived checks for Optional.of: non-null value is wrapped and null is rejected with NullPointerException
 * @run main OptionalWitupTest
 */

import java.util.Optional;

public class OptionalWitupTest {
    public static void main(String[] args) {
        Optional<String> present = Optional.of("value");
        if (!present.isPresent()) {
            throw new RuntimeException("Optional.of(non-null) must be present");
        }
        if (!"value".equals(present.get())) {
            throw new RuntimeException("Optional.of(non-null) must hold the given value");
        }

        String nothing = null;
        boolean thrown = false;
        try {
            Optional.of(nothing);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Optional.of(null) must throw NullPointerException");
        }
    }
}
