/*
 * @test
 * @summary Test Optional.orElseThrow behavior for present and empty values
 * @run main java.util.OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest {
    public static void main(String[] args) throws Throwable {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    static void testOrElseThrowWithValue() throws Throwable {
        Optional<String> opt = Optional.of("test");
        String result = opt.orElseThrow(new Supplier<Throwable>() {
            public Throwable get() {
                return new AssertionError("Should not be called");
            }
        });
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test' but got: " + result);
        }
    }

    static void testOrElseThrowWithoutValue() {
        Optional<String> opt = Optional.empty();
        try {
            opt.orElseThrow(new Supplier<RuntimeException>() {
                public RuntimeException get() {
                    return new RuntimeException("empty optional");
                }
            });
            throw new AssertionError("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if (!"empty optional".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
