/*
 * @test
 * @summary Test OptionalDouble.orElseThrow behavior for present and empty cases
 * @run main OptionalDoubleWitupTest
 */

import java.util.OptionalDouble;
import java.util.function.Supplier;

public class OptionalDoubleWitupTest {
    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowEmptyThrows();
    }

    private static void testOrElseThrowWithValue() {
        OptionalDouble opt = OptionalDouble.of(3.14);
        try {
            double val = opt.orElseThrow(new Supplier<RuntimeException>() {
                public RuntimeException get() {
                    return new RuntimeException("Should not be called");
                }
            });
            if (val != 3.14) {
                throw new AssertionError("Expected 3.14 but got " + val);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for present value", t);
        }
    }

    private static void testOrElseThrowEmptyThrows() {
        OptionalDouble emptyOpt = OptionalDouble.empty();
        final String msg = "expected exception";
        try {
            emptyOpt.orElseThrow(new Supplier<Exception>() {
                public Exception get() {
                    return new Exception(msg);
                }
            });
            throw new AssertionError("Expected exception was not thrown");
        } catch (Exception e) {
            if (!msg.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected throwable type", t);
        }
    }
}
