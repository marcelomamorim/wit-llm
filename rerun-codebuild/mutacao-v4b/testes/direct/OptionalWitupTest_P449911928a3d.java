/* @test
   @summary Test orElseThrow method of Optional.
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
        Optional<String> opt = Optional.of("test");
        String result = opt.orElseThrow(() -> new RuntimeException("No value"));
        assert "test".equals(result) : "Expected 'test', got " + result;
    }
    private static void testOrElseThrowWithEmpty() {
        Optional<String> opt = Optional.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
            throw new AssertionError("Expected RuntimeException");
        } catch (RuntimeException e) {
            assert "No value".equals(e.getMessage()) : "Unexpected message: " + e.getMessage();
        }
    }
}
