/* @test    @summary Test for Optional.orElseThrow method to ensure it throws the exception supplied when value is null.    @run main OptionalWitupTest */

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    static void testOrElseThrowWithValue() {
        Optional<String> opt = Optional.of("test");
        String result = opt.orElseThrow(() -> new RuntimeException("No value"));
        assert "test".equals(result) : "Expected 'test', got " + result;
    }

    static void testOrElseThrowWithoutValue() {
        Optional<String> opt = Optional.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
            assert false : "Expected RuntimeException to be thrown";
        } catch (RuntimeException e) {
            assert "No value".equals(e.getMessage()) : "Unexpected exception message: " + e.getMessage();
        }
    }
}
