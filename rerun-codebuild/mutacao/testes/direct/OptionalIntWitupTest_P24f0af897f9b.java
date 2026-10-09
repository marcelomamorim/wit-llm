/* @test    @summary Test OptionalInt.orElseThrow() method for present and absent values    @run main OptionalIntWitupTest_P24f0af897f9b */

import java.util.OptionalInt;
import java.util.function.Supplier;

public class OptionalIntWitupTest_P24f0af897f9b {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    static void testOrElseThrowWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        int result = opt.orElseThrow(() -> new RuntimeException("No value"));
        assert result == 42 : "Expected 42, got " + result;
    }

    static void testOrElseThrowWithoutValue() {
        OptionalInt opt = OptionalInt.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
            assert false : "Expected RuntimeException to be thrown";
        } catch (RuntimeException e) {
            assert e.getMessage().equals("No value") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
