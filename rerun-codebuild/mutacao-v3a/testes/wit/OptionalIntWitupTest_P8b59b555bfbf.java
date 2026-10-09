/* @test    @summary Testing OptionalInt.orElseThrow for exception path when no value is present    @run main OptionalIntWitupTest_P8b59b555bfbf */

import java.util.OptionalInt;
import java.util.function.Supplier;

public class OptionalIntWitupTest_P8b59b555bfbf {

    public static void main(String[] args) {
        testOrElseThrowWithNoValue();
        testOrElseThrowWithValue();
    }

    private static void testOrElseThrowWithNoValue() {
        OptionalInt emptyOpt = OptionalInt.empty();
        Supplier<RuntimeException> exceptionSupplier = () -> new RuntimeException("No value present");
        boolean exceptionThrown = false;
        try {
            emptyOpt.orElseThrow(exceptionSupplier);
        } catch (RuntimeException e) {
            exceptionThrown = true;
            assert "No value present".equals(e.getMessage()) : "Unexpected exception message";
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected RuntimeException was not thrown")
        }
    }

    private static void testOrElseThrowWithValue() {
        OptionalInt optWithValue = OptionalInt.of(42);
        int result = optWithValue.orElseThrow(() -> new RuntimeException("Should not throw"));
        assert result == 42 : "Expected value 42, but got " + result;
    }
}
