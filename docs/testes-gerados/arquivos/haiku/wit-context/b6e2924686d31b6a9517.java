/*
 * @test
 * @summary Witup-derived checks for JapaneseEra.of(int): valid era lookup and DateTimeException for out-of-range values
 * @run main JapaneseEraWitupTest
 */
import java.time.DateTimeException;
import java.time.chrono.JapaneseEra;

public class JapaneseEraWitupTest {
    public static void main(String[] args) {
        testValidMeijiReturnsSameInstance();
        testInvalidValuesThrow();
        testBoundaryBelowMeijiThrows();
        testExtremeValuesThrow();
    }

    static void testValidMeijiReturnsSameInstance() {
        JapaneseEra a = JapaneseEra.of(-1);
        JapaneseEra b = JapaneseEra.of(-1);
        if (a == null) {
            throw new RuntimeException("of(-1) returned null");
        }
        if (a != b) {
            throw new RuntimeException("of(-1) should return the same singleton instance");
        }
    }

    static void testInvalidValuesThrow() {
        expectInvalid(1000);
        expectInvalid(-1000);
    }

    static void testBoundaryBelowMeijiThrows() {
        expectInvalid(-2);
    }

    static void testExtremeValuesThrow() {
        expectInvalid(Integer.MIN_VALUE);
        expectInvalid(Integer.MAX_VALUE);
    }

    static void expectInvalid(int value) {
        try {
            JapaneseEra.of(value);
        } catch (DateTimeException e) {
            String expected = "Invalid era: " + value;
            if (!expected.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for " + value + ": " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected DateTimeException for of(" + value + ")");
    }
}
