/*
 * @test
 * @summary Test OptionalDouble.orElseThrow behavior for present and empty values
 * @run main OptionalDoubleWitupTest
 */

import java.util.OptionalDouble;
import java.util.function.Supplier;

public class OptionalDoubleWitupTest {
    public static void main(String[] args) throws Throwable {
        testOrElseThrowWithValue();
        testOrElseThrowEmptyThrows();
    }

    static void testOrElseThrowWithValue() throws Throwable {
        OptionalDouble opt = OptionalDouble.of(3.14);
        double val = opt.orElseThrow(new Supplier<Throwable>() {
            public Throwable get() {
                return new AssertionError("Should not be called");
            }
        });
        if (val != 3.14) {
            throw new AssertionError("Expected 3.14 but got " + val);
        }
    }

    static void testOrElseThrowEmptyThrows() {
        OptionalDouble empty = OptionalDouble.empty();
        Supplier<RuntimeException> exSupplier = new Supplier<RuntimeException>() {
            public RuntimeException get() {
                return new RuntimeException("No value present");
            }
        };
        try {
            empty.orElseThrow(exSupplier);
            throw new AssertionError("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            if (!"No value present".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
