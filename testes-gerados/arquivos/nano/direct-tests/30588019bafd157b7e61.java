/* @test    @summary Test for Optional.orElseThrow() method behavior    @run main OptionalWitupTest */

import java.util.Optional;
import java.util.NoSuchElementException;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        Optional<String> opt = Optional.of("test");
        String result = opt.orElseThrow();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', but got: " + result);
        }
    }

    private static void testOrElseThrowWithoutValue() {
        Optional<String> opt = Optional.empty();
        try {
            opt.orElseThrow();
            throw new AssertionError("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
