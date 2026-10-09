/* @test
   @summary Testa o método orElseThrow de Optional para comportamento esperado.
   @run main OptionalWitupTest_P449911928a3d
 */
import java.util.Optional;
import java.util.function.Supplier;
public class OptionalWitupTest_P449911928a3d {
    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithEmpty();
    }
    private static void testOrElseThrowWithValue() {
        Optional<String> opt = Optional.of("value");
        String result = opt.orElseThrow(() -> new RuntimeException("should not throw"));
        if (!"value".equals(result)) {
            throw new AssertionError("Expected 'value', got: " + result);
        }
    }
    private static void testOrElseThrowWithEmpty() {
        Optional<String> opt = Optional.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("test exception"));
            throw new AssertionError("Expected exception was not thrown");
        } catch (RuntimeException e) {
            if (!"test exception".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
