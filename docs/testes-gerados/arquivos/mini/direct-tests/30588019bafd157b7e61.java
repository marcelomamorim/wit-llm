/*
 * @test
 * @summary Test Optional.orElseThrow() behavior for present and empty values
 * @run main java.util.OptionalWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowEmpty();
    }

    private static void testOrElseThrowWithValue() {
        Optional<String> opt = Optional.of("test");
        String result = opt.orElseThrow();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test' but got: " + result);
        }
    }

    private static void testOrElseThrowEmpty() {
        Optional<String> opt = Optional.empty();
        try {
            opt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException for empty Optional");
        } catch (NoSuchElementException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("No value present")) {
                throw new AssertionError("Exception message mismatch: " + msg);
            }
        }
    }
}
