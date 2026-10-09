/* @test
   @summary Verifies that orElseThrow throws the exception supplied when OptionalInt is empty.
   @run main OptionalIntWitupTest_P9aa2575b12db
 */
import java.util.OptionalInt;
import java.util.function.Supplier;

public class OptionalIntWitupTest_P9aa2575b12db {
    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        OptionalInt opt = OptionalInt.of(42);
        int result = opt.orElseThrow(() -> new RuntimeException("No value"));
        assert result == 42 : "Expected 42, got " + result;
    }

    private static void testOrElseThrowWithoutValue() {
        OptionalInt opt = OptionalInt.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
            throw new AssertionError("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assert e.getMessage().equals("No value") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
