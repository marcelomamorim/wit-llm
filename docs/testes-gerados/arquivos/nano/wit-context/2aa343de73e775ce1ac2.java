/* @test    @summary Test for ValueRange.checkValidValue method to verify exception throwing on invalid values    @run main ValueRangeWitupTest */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testCheckValidValueThrowsException();
        testCheckValidValueValid();
    }

    private static void testCheckValidValueThrowsException() {
        ValueRange range = ValueRange.of(1, 10);
        TemporalField field = null; // não utilizado no teste, apenas para compatibilidade
        long invalidValue = 0; // valor abaixo do mínimo
        try {
            range.checkValidValue(invalidValue, field);
            System.err.println("Failed: Expected DateTimeException for value " + invalidValue);
        } catch (DateTimeException e) {
            System.out.println("Passed: Caught expected DateTimeException for value " + invalidValue);
        }
        long tooLargeValue = 11; // valor acima do máximo
        try {
            range.checkValidValue(tooLargeValue, field);
            System.err.println("Failed: Expected DateTimeException for value " + tooLargeValue);
        } catch (DateTimeException e) {
            System.out.println("Passed: Caught expected DateTimeException for value " + tooLargeValue);
        }
    }

    private static void testCheckValidValueValid() {
        ValueRange range = ValueRange.of(1, 10);
        TemporalField field = null; // não utilizado no teste
        long validValue = 5;
        long result = range.checkValidValue(validValue, field);
        if (result == validValue) {
            System.out.println("Passed: Valid value " + validValue + " returned correctly.");
        } else {
            System.err.println("Failed: Valid value " + validValue + " did not return correctly.");
        }
    }
}
