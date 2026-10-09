/*
 * @test
 * @summary Test Optional.orElseThrow method to ensure it throws the exception supplied when value is null
 * @run main OptionalWitupTest_Pb3e303f30864
 */

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest_Pb3e303f30864 {
    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    static void testOrElseThrowWithValue() {
        Optional<String> opt = Optional.of("Hello");
        String result = opt.orElseThrow(() -> new RuntimeException("No value present"));
        if (!"Hello".equals(result)) {
            throw new AssertionError("Expected 'Hello', got: " + result);
        }
    }

    static void testOrElseThrowWithoutValue() {
        Optional<String> opt = Optional.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
            throw new AssertionError("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            if (!"No value".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
