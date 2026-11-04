/*
 * @test
 * @summary Test Optional.get() method behavior
 * @run main java.util.OptionalWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testGetPresentValue();
        testGetEmptyThrows();
    }

    private static void testGetPresentValue() {
        Optional<String> opt = Optional.of("test");
        String value = opt.get();
        if (!"test".equals(value)) {
            throw new AssertionError("Expected 'test' but got: " + value);
        }
    }

    private static void testGetEmptyThrows() {
        Optional<Object> emptyOpt = Optional.empty();
        try {
            emptyOpt.get();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("No value present")) {
                throw new AssertionError("Exception message mismatch: " + msg);
            }
        }
    }
}
