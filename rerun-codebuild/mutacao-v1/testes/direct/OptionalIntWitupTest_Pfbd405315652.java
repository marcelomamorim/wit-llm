/* @test    @summary Test OptionalInt.orElseThrow method for present and absent values    @run main OptionalIntWitupTest_Pfbd405315652 */

import java.util.OptionalInt;
import java.util.function.Supplier;

public class OptionalIntWitupTest_Pfbd405315652 {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    static void testOrElseThrowWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        int result = opt.orElseThrow(() -> new RuntimeException("No value"));
        if (result != 42) {
            throw new AssertionError("Expected 42, got " + result);
        }
    }

    static void testOrElseThrowWithoutValue() {
        OptionalInt opt = OptionalInt.empty();
        boolean exceptionThrown = false;
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
        } catch (RuntimeException e) {
            exceptionThrown = true;
            if (!"No value".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected RuntimeException to be thrown");
        }
    }
}
