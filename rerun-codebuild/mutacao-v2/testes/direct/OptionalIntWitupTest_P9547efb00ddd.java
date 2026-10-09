/*
 * @test
 * @summary Test OptionalInt.orElseThrow behavior for present and empty values
 * @run main OptionalIntWitupTest_P9547efb00ddd
 */

import java.util.OptionalInt;
import java.util.function.Supplier;

public class OptionalIntWitupTest_P9547efb00ddd {
    public static void main(String[] args) throws Throwable {
        testOrElseThrowWithValue();
        testOrElseThrowEmptyThrows();
    }

    static void testOrElseThrowWithValue() throws Throwable {
        OptionalInt opt = OptionalInt.of(42);
        int result = opt.orElseThrow(new Supplier<Throwable>() {
            public Throwable get() {
                return new AssertionError("Should not be called");
            }
        });
        if (result != 42) {
            throw new AssertionError("Expected 42 but got " + result);
        }
    }

    static void testOrElseThrowEmptyThrows() {
        OptionalInt emptyOpt = OptionalInt.empty();
        try {
            emptyOpt.orElseThrow(new Supplier<Throwable>() {
                public Throwable get() {
                    return new IllegalStateException("No value present");
                }
            });
            throw new AssertionError("Expected exception was not thrown");
        } catch (IllegalStateException e) {
            if (!"No value present".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
