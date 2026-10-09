/*
 * @test
 * @summary Test for ValueRange.checkValidValue(long, TemporalField)
 * @run main ValueRangeWitupTest_P0842f2cd63db
 */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest_P0842f2cd63db {

    public static void main(String[] args) {
        testCheckValidValueWithinRange();
        testCheckValidValueBelowRange();
        testCheckValidValueAboveRange();
    }

    private static void testCheckValidValueWithinRange() {
        try {
            // Valor válido, método deve retornar o valor sem lançar exceção
            long value = 10L;
            // Como não temos detalhes do campo, usaremos null, assumindo que o método aceita isso para fins de teste
            long result = ValueRange.of(5L, 15L).checkValidValue(value, null);
            if (result != value) {
                throw new AssertionError("Expected result to be " + value + " but was " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value", e);
        }
    }

    private static void testCheckValidValueBelowRange() {
        try {
            long value = 4L;
            ValueRange range = ValueRange.of(5L, 15L);
            // Como o valor está fora do range, espera-se uma exceção DateTimeException
            range.checkValidValue(value, null);
            throw new AssertionError("Expected DateTimeException for value below range");
        } catch (DateTimeException e) {
            // Esperado
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testCheckValidValueAboveRange() {
        try {
            long value = 16L;
            ValueRange range = ValueRange.of(5L, 15L);
            // Valor acima do range, espera-se exceção
            range.checkValidValue(value, null);
            throw new AssertionError("Expected DateTimeException for value above range");
        } catch (DateTimeException e) {
            // Esperado
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
