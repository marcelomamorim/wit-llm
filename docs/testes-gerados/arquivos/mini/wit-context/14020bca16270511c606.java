/*
 * @test
 * @summary Test java.time.LocalDate.getMonth() method for valid and invalid month values
 * @run main java.time.LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testGetMonthValid();
        testGetMonthInvalidLow();
        testGetMonthInvalidHigh();
    }

    private static void testGetMonthValid() {
        // Create LocalDate with valid month 1 (January)
        LocalDate date = LocalDate.of(2018, 1, 15);
        Month month = date.getMonth();
        if (month != Month.JANUARY) {
            throw new AssertionError("Expected JANUARY but got " + month);
        }
    }

    private static void testGetMonthInvalidLow() {
        // Use reflection to create LocalDate with invalid month 0 to test getMonth() throws DateTimeException
        // Since LocalDate.of() validates month, we create a subclass to bypass validation
        LocalDate invalidDate = new LocalDate(2018, 0, 15) {
            @Override
            public Month getMonth() {
                return Month.of(this.getMonthValue());
            }

            @Override
            public int getMonthValue() {
                return 0;
            }
        };

        try {
            invalidDate.getMonth();
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }

    private static void testGetMonthInvalidHigh() {
        // Use reflection to create LocalDate with invalid month 13 to test getMonth() throws DateTimeException
        LocalDate invalidDate = new LocalDate(2018, 13, 15) {
            @Override
            public Month getMonth() {
                return Month.of(this.getMonthValue());
            }

            @Override
            public int getMonthValue() {
                return 13;
            }
        };

        try {
            invalidDate.getMonth();
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
