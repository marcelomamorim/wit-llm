/*
 * @test
 * @summary Test OptionalLong.orElseThrow behavior for present and empty cases
 * @run main OptionalLongWitupTest_P8fad952facdc
 */

import java.util.OptionalLong;
import java.util.function.Supplier;

public class OptionalLongWitupTest_P8fad952facdc {
    public static void main(String[] args) throws Throwable {
        testOrElseThrowWithValuePresent();
        testOrElseThrowWithEmptyThrows();
    }

    static void testOrElseThrowWithValuePresent() throws Throwable {
        final long testValue = 12345L;
        OptionalLong opt = OptionalLong.of(testValue);
        // Should return the contained value without throwing
        long result = opt.orElseThrow(new Supplier<Throwable>() {
            public Throwable get() {
                return new AssertionError("Should not throw when value is present");
            }
        });
        if (result != testValue) {
            throw new AssertionError("Expected value " + testValue + " but got " + result);
        }
    }

    static void testOrElseThrowWithEmptyThrows() {
        OptionalLong emptyOpt = OptionalLong.empty();
        Supplier<IllegalStateException> exceptionSupplier = new Supplier<IllegalStateException>() {
            public IllegalStateException get() {
                return new IllegalStateException("No value present");
            }
        };

        try {
            emptyOpt.orElseThrow(exceptionSupplier);
            throw new AssertionError("Expected exception was not thrown");
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (!"No value present".equals(msg)) {
                throw new AssertionError("Exception message mismatch: " + msg);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
