/*
 * @test
 * @summary Tests for java.util.Optional.orElseThrow method
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithEmpty();
    }

    private static void testOrElseThrowWithValue() {
        Optional<String> opt = Optional.of("test");
        String result = opt.orElseThrow(() -> new RuntimeException("No value"));
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', got: " + result);
        }
    }

    private static void testOrElseThrowWithEmpty() {
        Optional<String> opt = Optional.empty();
        boolean exceptionThrown = false;
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
        } catch (RuntimeException e) {
            exceptionThrown = "No value".equals(e.getMessage());
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected RuntimeException with message 'No value'");
        }
    }
}
