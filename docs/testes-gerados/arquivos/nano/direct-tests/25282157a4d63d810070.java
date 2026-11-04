/* @test    @summary Test OptionalDouble.orElseThrow method for present and absent values    @run main OptionalDoubleWitupTest */

import java.util.OptionalDouble;
import java.util.function.Supplier;

public class OptionalDoubleWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    static void testOrElseThrowWithValue() {
        OptionalDouble opt = OptionalDouble.of(3.14);
        double result = opt.orElseThrow(() -> new RuntimeException("No value"));
        assert result == 3.14 : "Expected 3.14, got " + result;
    }

    static void testOrElseThrowWithoutValue() {
        OptionalDouble opt = OptionalDouble.empty();
        boolean exceptionThrown = false;
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
        } catch (RuntimeException e) {
            exceptionThrown = true;
            assert e.getMessage().equals("No value") : "Unexpected exception message: " + e.getMessage();
        }
        assert exceptionThrown : "Expected RuntimeException to be thrown";
    }
}
