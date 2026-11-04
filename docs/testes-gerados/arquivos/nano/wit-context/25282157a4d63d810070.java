/* @test    @summary Test for OptionalDouble.orElseThrow() method behavior.    @run main OptionalDoubleWitupTest */

import java.util.OptionalDouble;
import java.util.function.Supplier;

public class OptionalDoubleWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        OptionalDouble opt = OptionalDouble.of(3.14);
        double result = opt.orElseThrow(() -> new RuntimeException("No value present"));
        if (result != 3.14) {
            throw new AssertionError("Expected 3.14, but got " + result);
        }
    }

    private static void testOrElseThrowWithoutValue() {
        OptionalDouble opt = OptionalDouble.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
            throw new AssertionError("Expected exception was not thrown");
        } catch (RuntimeException e) {
            if (!"No value".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
