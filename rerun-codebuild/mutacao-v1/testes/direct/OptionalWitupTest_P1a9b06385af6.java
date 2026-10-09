/* @test    @summary Test Optional.orElseThrow() method behavior in various scenarios    @run main OptionalWitupTest_P1a9b06385af6 */

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest_P1a9b06385af6 {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithEmpty();
        System.out.println("All tests passed.");
    }

    private static void testOrElseThrowWithValue() {
        Optional<String> opt = Optional.of("test");
        String result = opt.orElseThrow(() -> new RuntimeException("Should not throw"));
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', got: " + result);
        }
    }

    private static void testOrElseThrowWithEmpty() {
        Optional<String> opt = Optional.empty();
        boolean exceptionThrown = false;
        try {
            opt.orElseThrow(() -> new RuntimeException("No value present"));
        } catch (RuntimeException e) {
            exceptionThrown = true;
            if (!"No value present".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected RuntimeException to be thrown");
        }
    }
}
