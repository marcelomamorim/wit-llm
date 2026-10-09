/* @test
   @summary Verifica comportamento do método of de DayOfWeek com entradas inválidas.
   @run main DayOfWeekWitupTest_P9f611ab04b72
 */
import java.time.DayOfWeek;
import java.time.DateTimeException;

public class DayOfWeekWitupTest_P9f611ab04b72 {
    public static void main(String[] args) {
        testInvalidDayOfWeekBelowRange();
        testInvalidDayOfWeekAboveRange();
        testValidDayOfWeek();
    }

    static void testInvalidDayOfWeekBelowRange() {
        try {
            DayOfWeek.of(0);
            throw new AssertionError("Expected DateTimeException for dayOfWeek=0");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidDayOfWeekAboveRange() {
        try {
            DayOfWeek.of(8);
            throw new AssertionError("Expected DateTimeException for dayOfWeek=8");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testValidDayOfWeek() {
        for (int i = 1; i <= 7; i++) {
            DayOfWeek dow = DayOfWeek.of(i);
            if (dow.getValue() != i) {
                throw new AssertionError("Unexpected value for day: " + dow);
            }
        }
    }
}
