/* @test    @summary Test for Optional.orElseThrow() method, covering null value case    @run main OptionalWitupTest_Pb3e303f30864 */

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest_Pb3e303f30864 {

    public static void main(String[] args) {
        testOrElseThrowWithNullValue();
        testOrElseThrowWithNonNullValue();
    }

    public static void testOrElseThrowWithNullValue() {
        Optional<String> opt = Optional.empty();
        Supplier<RuntimeException> supplier = () -> new RuntimeException("No value present");
        try {
            opt.orElseThrow(supplier);
            throw new AssertionError("Expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            assert e.getMessage().equals("No value present") : "Unexpected exception message: " + e.getMessage();
        }
    }

    public static void testOrElseThrowWithNonNullValue() {
        Optional<String> opt = Optional.of("test");
        Supplier<RuntimeException> supplier = () -> new RuntimeException("No value present");
        String result = opt.orElseThrow(supplier);
        assert result.equals("test") : "Expected 'test', got: " + result;
    }
}
