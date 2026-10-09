/* @test    @summary Test for OptionalLong.orElseThrow method to verify exception path when value is absent    @run main OptionalLongWitupTest_P6b0f9dddbe93 */

import java.util.OptionalLong;
import java.util.function.Supplier;

public class OptionalLongWitupTest_P6b0f9dddbe93 {

    public static void main(String[] args) {
        testOrElseThrowThrowsExceptionWhenEmpty();
        testOrElseThrowReturnsValueWhenPresent();
    }

    private static void testOrElseThrowThrowsExceptionWhenEmpty() {
        OptionalLong opt = OptionalLong.empty();
        Supplier<RuntimeException> supplier = () -> new RuntimeException("No value present");
        try {
            opt.orElseThrow(supplier);
            throw new AssertionError("Expected exception was not thrown");
        } catch (RuntimeException e) {
            if (!"No value present".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testOrElseThrowReturnsValueWhenPresent() {
        OptionalLong opt = OptionalLong.of(42L);
        Supplier<RuntimeException> supplier = () -> new RuntimeException("No value present");
        long result = opt.orElseThrow(supplier);
        if (result != 42L) {
            throw new AssertionError("Expected 42L, but got: " + result);
        }
    }
}
