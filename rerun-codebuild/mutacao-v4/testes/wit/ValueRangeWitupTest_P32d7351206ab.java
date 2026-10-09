/* @test
   @summary Verifies that checkValidValue throws DateTimeException for invalid values.
   @run main ValueRangeWitupTest_P32d7351206ab
 */
import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest_P32d7351206ab {
    public static void main(String[] args) {
        testCheckValidValueThrows();
    }

    private static void testCheckValidValueThrows() {
        ValueRange range = ValueRange.of(1, 10);
        long invalidValueBelow = 0;
        long invalidValueAbove = 11;
        boolean exceptionThrownBelow = false;
        boolean exceptionThrownAbove = false;
        try {
            range.checkValidValue(invalidValueBelow, null);
        } catch (DateTimeException e) {
            exceptionThrownBelow = true;
        }
        try {
            range.checkValidValue(invalidValueAbove, null);
        } catch (DateTimeException e) {
            exceptionThrownAbove = true;
        }
        if (!exceptionThrownBelow) {
            throw new AssertionError("Expected DateTimeException for value below range");
        }
        if (!exceptionThrownAbove) {
            throw new AssertionError("Expected DateTimeException for value above range");
        }
    }
}
