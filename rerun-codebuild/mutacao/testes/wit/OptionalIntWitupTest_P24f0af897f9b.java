/* @test    @summary Test for OptionalInt.orElseThrow method to verify exception throwing when value is absent    @run main OptionalIntWitupTest_P24f0af897f9b */

import java.util.OptionalInt;
import java.util.function.Supplier;

public class OptionalIntWitupTest_P24f0af897f9b {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        try {
            int result = opt.orElseThrow(() -> new RuntimeException("No value present"));
            if (result != 42) {
                throw new AssertionError("Expected 42, got " + result);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception when value is present", t);
        }
    }

    private static void testOrElseThrowWithoutValue() {
        OptionalInt opt = OptionalInt.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
            throw new AssertionError("Expected exception was not thrown");
        } catch (RuntimeException e) {
            if (!"No value".equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type", t);
        }
    }
}
