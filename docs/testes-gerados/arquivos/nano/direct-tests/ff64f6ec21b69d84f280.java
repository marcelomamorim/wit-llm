/* @test    @summary Test for OptionalLong.orElseThrow method behavior    @run main OptionalLongWitupTest */

import java.util.OptionalLong;
import java.util.function.Supplier;

public class OptionalLongWitupTest {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    static void testOrElseThrowWithValue() {
        OptionalLong opt = OptionalLong.of(42L);
        long result = opt.orElseThrow(() -> new RuntimeException("No value"));
        if (result != 42L) {
            throw new AssertionError("Expected 42, got " + result);
        }
    }

    static void testOrElseThrowWithoutValue() {
        OptionalLong opt = OptionalLong.empty();
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
