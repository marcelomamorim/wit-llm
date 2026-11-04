/*
 * @test
 * @summary Test java.util.Optional.orElseThrow(Supplier) for expected behavior and exception
 * @run main java.util.OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest {
    public static void main(String[] args) throws Throwable {
        testOrElseThrowReturnsValueWhenPresent();
        testOrElseThrowThrowsWhenEmpty();
        System.out.println("All tests passed.");
    }

    static void testOrElseThrowReturnsValueWhenPresent() throws Throwable {
        Optional<String> opt = Optional.of("test");
        String result = opt.orElseThrow(new Supplier<Throwable>() {
            public Throwable get() {
                return new AssertionError("Should not be called");
            }
        });
        if (!"test".equals(result)) {
            throw new AssertionError("Expected value 'test', got: " + result);
        }
    }

    static void testOrElseThrowThrowsWhenEmpty() {
        Optional<String> opt = Optional.empty();
        Supplier<RuntimeException> supplier = new Supplier<RuntimeException>() {
            public RuntimeException get() {
                return new RuntimeException("empty optional");
            }
        };
        try {
            opt.orElseThrow(supplier);
            throw new AssertionError("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            if (!"empty optional".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
