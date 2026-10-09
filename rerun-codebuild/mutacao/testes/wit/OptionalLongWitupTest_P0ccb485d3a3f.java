/* @test    @summary Test for OptionalLong.orElseThrow method to verify exception throwing when value is absent    @run main OptionalLongWitupTest_P0ccb485d3a3f */

import java.util.OptionalLong;
import java.util.function.Supplier;

public class OptionalLongWitupTest_P0ccb485d3a3f {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        OptionalLong opt = OptionalLong.of(42L);
        long result = opt.orElseThrow(() -> new RuntimeException("Should not throw"));
        if (result != 42L) {
            throw new AssertionError("Expected 42, got " + result);
        }
    }

    private static void testOrElseThrowWithoutValue() {
        OptionalLong opt = OptionalLong.empty();
        boolean exceptionThrown = false;
        try {
            opt.orElseThrow(() -> new RuntimeException("No value present"));
        } catch (RuntimeException e) {
            exceptionThrown = true;
            if (!"No value present".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected RuntimeException to be thrown");
        }
    }
}
