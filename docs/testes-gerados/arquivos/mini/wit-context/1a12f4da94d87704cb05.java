/*
 * @test
 * @summary Test OptionalInt.orElseThrow behavior including exception throwing
 * @run main OptionalIntWitupTest
 */

import java.util.OptionalInt;
import java.util.function.Supplier;

public class OptionalIntWitupTest {
    public static void main(String[] args) {
        testOrElseThrowReturnsValueWhenPresent();
        testOrElseThrowThrowsWhenEmpty();
    }

    private static void testOrElseThrowReturnsValueWhenPresent() {
        OptionalInt opt = OptionalInt.of(42);
        try {
            int val = opt.orElseThrow(() -> new IllegalStateException("Should not throw"));
            if (val != 42) {
                throw new AssertionError("Expected 42 but got " + val);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception when value is present", t);
        }
    }

    private static void testOrElseThrowThrowsWhenEmpty() {
        OptionalInt emptyOpt = OptionalInt.empty();
        Supplier<IllegalArgumentException> exSupplier = () -> new IllegalArgumentException("No value present");
        try {
            emptyOpt.orElseThrow(exSupplier);
            throw new AssertionError("Expected exception was not thrown");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (!"No value present".equals(msg)) {
                throw new AssertionError("Exception message mismatch: " + msg);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type thrown", t);
        }
    }
}
