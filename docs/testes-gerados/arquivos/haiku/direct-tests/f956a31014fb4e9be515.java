/*
 * @test
 * @summary Verify Month.of(int) returns the matching constants and rejects out-of-range values with DateTimeException
 * @run main MonthWitupTest
 */

import java.time.DateTimeException;
import java.time.Month;

public class MonthWitupTest {

    public static void main(String[] args) {
        Month[] values = Month.values();
        if (values.length != 12) {
            throw new AssertionError("Expected 12 months but found " + values.length);
        }
        for (int i = 1; i <= 12; i++) {
            Month m = Month.of(i);
            if (m != values[i - 1]) {
                throw new AssertionError("Month.of(" + i + ") returned unexpected constant: " + m);
            }
            if (m.getValue() != i) {
                throw new AssertionError("Month.of(" + i + ").getValue() returned " + m.getValue());
            }
        }
        if (Month.of(1) != Month.JANUARY) {
            throw new AssertionError("Month.of(1) is not JANUARY");
        }
        if (Month.of(12) != Month.DECEMBER) {
            throw new AssertionError("Month.of(12) is not DECEMBER");
        }
        expectInvalid(0);
        expectInvalid(13);
        expectInvalid(-1);
        expectInvalid(Integer.MIN_VALUE);
        expectInvalid(Integer.MAX_VALUE);
    }

    static void expectInvalid(int value) {
        try {
            Month m = Month.of(value);
            throw new AssertionError("No exception for Month.of(" + value + "), returned " + m);
        } catch (DateTimeException e) {
            String expectedMessage = "Invalid value for MonthOfYear: " + value;
            if (!expectedMessage.equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }
}
