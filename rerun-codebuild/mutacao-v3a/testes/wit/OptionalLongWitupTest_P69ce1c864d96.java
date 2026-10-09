/*
 * @test
 * @summary Tests for java.util.OptionalLong's orElseThrow method
 * @run main OptionalLongWitupTest_P69ce1c864d96
 */
import java.util.OptionalLong;
import java.util.function.Supplier;

public class OptionalLongWitupTest_P69ce1c864d96 {

    public static void main(String[] args) {
        testOrElseThrowWhenPresent();
        testOrElseThrowWhenEmpty();
    }

    static void testOrElseThrowWhenPresent() {
        OptionalLong opt = OptionalLong.of(42L);
        try {
            long result = opt.orElseThrow(() -> new RuntimeException("Should not throw"));
            if (result != 42L) {
                throw new AssertionError("Expected 42, got: " + result);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    static void testOrElseThrowWhenEmpty() {
        OptionalLong opt = OptionalLong.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("No value"));
            throw new AssertionError("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            if (!"No value".equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch: " + e.getMessage());
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
