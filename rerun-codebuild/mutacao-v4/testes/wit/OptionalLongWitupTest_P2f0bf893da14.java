/* @test
   @summary Testa o método orElseThrow de OptionalLong para comportamento de exceção.
   @run main OptionalLongWitupTest_P2f0bf893da14
 */
import java.util.OptionalLong;
import java.util.function.Supplier;
public class OptionalLongWitupTest_P2f0bf893da14 {
    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }
    private static void testOrElseThrowWithValue() {
        OptionalLong opt = OptionalLong.of(42L);
        long result = opt.orElseThrow(() -> new RuntimeException("No value"));
        if (result != 42L) {
            throw new AssertionError("Expected 42, got " + result);
        }
    }
    private static void testOrElseThrowWithoutValue() {
        OptionalLong opt = OptionalLong.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
            throw new AssertionError("Expected exception was not thrown");
        } catch (RuntimeException e) {
            if (!"No value".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
