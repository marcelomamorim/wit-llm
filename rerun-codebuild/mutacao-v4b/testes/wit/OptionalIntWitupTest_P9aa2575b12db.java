/* @test
   @summary Testa o método orElseThrow de OptionalInt.
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
        int result = opt.orElseThrow(() -> new RuntimeException("No value present"));
        if (result != 42) {
            throw new AssertionError("Expected 42, got " + result);
        }
    }
    private static void testOrElseThrowWithoutValue() {
        OptionalInt opt = OptionalInt.empty();
        boolean exceptionThrown = false;
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
        } catch (RuntimeException e) {
            exceptionThrown = true;
            if (!e.getMessage().equals("No value")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected RuntimeException to be thrown");
        }
    }
}
