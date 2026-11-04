/*
 * @test
 * @summary Test OptionalLong.orElseThrow behavior for present and empty values
 * @run main OptionalLongWitupTest
 */

import java.util.OptionalLong;
import java.util.function.Supplier;

public class OptionalLongWitupTest {
    public static void main(String[] args) throws Throwable {
        testOrElseThrowWithValue();
        testOrElseThrowEmptyThrows();
    }

    static void testOrElseThrowWithValue() throws Throwable {
        OptionalLong opt = OptionalLong.of(123L);
        long val = opt.orElseThrow(new Supplier<Throwable>() {
            public Throwable get() {
                return new AssertionError("Should not be called");
            }
        });
        if (val != 123L) {
            throw new AssertionError("Expected 123L but got " + val);
        }
    }

    static void testOrElseThrowEmptyThrows() {
        OptionalLong empty = OptionalLong.empty();
        try {
            empty.orElseThrow(new Supplier<Throwable>() {
                public Throwable get() {
                    return new IllegalStateException("No value present");
                }
            });
            throw new AssertionError("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            if (!"No value present".equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch: " + e.getMessage());
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
