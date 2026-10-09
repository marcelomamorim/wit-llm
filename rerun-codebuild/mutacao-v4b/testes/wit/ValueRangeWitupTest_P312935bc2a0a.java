/* @test
   @summary Testa o método checkValidIntValue para verificar se lança exceção DateTimeException ao receber valores inválidos.
   @run main ValueRangeWitupTest_P312935bc2a0a
 */
import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest_P312935bc2a0a {
    public static void main(String[] args) {
        testCheckValidIntValueThrows();
        testCheckValidIntValueAccepts();
    }

    private static void testCheckValidIntValueThrows() {
        long minSmallest = Long.MIN_VALUE + 1;
        long maxLargest = Long.MAX_VALUE - 1;
        long valueBelowMin = minSmallest - 1;
        long valueAboveMax = maxLargest + 1;
        TemporalField dummyField = null; // não utilizado na validação
        boolean threw = false;
        try {
            // Valor abaixo do limite inferior esperado
            ValueRange.of(0, 0).checkValidIntValue(valueBelowMin, dummyField);
        } catch (DateTimeException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected DateTimeException for value below min");
        }
        threw = false;
        try {
            // Valor acima do limite superior esperado
            ValueRange.of(0, 0).checkValidIntValue(valueAboveMax, dummyField);
        } catch (DateTimeException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected DateTimeException for value above max");
        }
    }

    private static void testCheckValidIntValueAccepts() {
        long validValue = 100L;
        TemporalField dummyField = null; // não utilizado na validação
        try {
            int result = ValueRange.of(0, 200).checkValidIntValue(validValue, dummyField);
            if (result != validValue) {
                throw new AssertionError("Returned value does not match input");
            }
        } catch (Exception e) {
            throw new AssertionError("Did not expect exception for valid value", e);
        }
    }
}
