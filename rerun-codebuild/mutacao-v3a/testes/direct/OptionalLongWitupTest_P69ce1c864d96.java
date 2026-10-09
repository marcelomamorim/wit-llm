/* @test @summary Test orElseThrow method of OptionalLong class @run main OptionalLongWitupTest_P69ce1c864d96 */

import java.util.OptionalLong;
import java.util.function.Supplier;

public class OptionalLongWitupTest_P69ce1c864d96 {

    public static void main(String[] args) {
        testOrElseThrowWithValue();
        testOrElseThrowWithoutValue();
    }

    private static void testOrElseThrowWithValue() {
        OptionalLong opt = OptionalLong.of(42L);
        long result = opt.orElseThrow(() -> new RuntimeException("Should not throw"));
        assert result == 42L : "Expected value 42, but got " + result;
    }

    private static void testOrElseThrowWithoutValue() {
        OptionalLong opt = OptionalLong.empty();
        try {
            opt.orElseThrow(() -> new RuntimeException("No value present"));
            assert false : "Expected RuntimeException was not thrown";
        } catch (RuntimeException e) {
            assert "No value present".equals(e.getMessage()) : "Unexpected exception message: " + e.getMessage();
        }
    }
}
