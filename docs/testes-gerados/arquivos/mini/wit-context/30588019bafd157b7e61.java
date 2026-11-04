/*
 * @test
 * @summary Test Optional.orElseThrow() behavior including exception throwing when empty
 * @run main OptionalWitupTest
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
        String value = opt.orElseThrow();
        if (!"test".equals(value)) {
            throw new AssertionError("Expected value 'test', got: " + value);
        }
    }

    private static void testOrElseThrowEmpty() {
        Optional<String> emptyOpt = Optional.empty();
        try {
            emptyOpt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("No value present")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }
}
