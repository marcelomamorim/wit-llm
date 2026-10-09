/* @test    @summary Test for ValueRange.checkValidValue() method to verify exception throwing on invalid values    @run main ValueRangeWitupTest_Pde142742ee04 */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest_Pde142742ee04 {

    public static void main(String[] args) {
        testCheckValidValueThrowsOnInvalid();
        testCheckValidValueReturnsOnValid();
    }

    static void testCheckValidValueThrowsOnInvalid() {
        ValueRange range = ValueRange.of(1, 10);
        TemporalField field = null; // não utilizado neste teste, apenas para preencher assinatura
        long invalidValueBelow = 0; // abaixo do mínimo
        long invalidValueAbove = 11; // acima do máximo
        try {
            range.checkValidValue(invalidValueBelow, field);
            throw new AssertionError("Expected DateTimeException for value below range");
        } catch (DateTimeException e) {
            // esperado
        }
        try {
            range.checkValidValue(invalidValueAbove, field);
            throw new AssertionError("Expected DateTimeException for value above range");
        } catch (DateTimeException e) {
            // esperado
        }
    }

    static void testCheckValidValueReturnsOnValid() {
        ValueRange range = ValueRange.of(1, 10);
        TemporalField field = null; // não utilizado neste teste
        long validValue = 5;
        long result = range.checkValidValue(validValue, field);
        if (result != validValue) {
            throw new AssertionError("Returned value does not match input");
        }
    }
}
