/* @test    @summary Test for OptionalInt.orElseThrow method behavior.    @run main OptionalIntWitupTest_Pfbd405315652 */

import java.util.OptionalInt;
import java.util.function.Supplier;

public class OptionalIntWitupTest_Pfbd405315652 {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        int result = opt.orElseThrow(() -> new RuntimeException("Should not throw"));
        if (result != 42) {
            throw new AssertionError("Expected 42, got " + result);
        }
    }

    private static void testOrElseThrowWithoutValue() {
        OptionalInt emptyOpt = OptionalInt.empty();
        try {
            emptyOpt.orElseThrow(() -> new RuntimeException("No value present"));
            throw new AssertionError("Expected exception was not thrown");
        } catch (RuntimeException e) {
            if (!"No value present".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
