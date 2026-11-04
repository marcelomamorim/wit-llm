/*
 * @test
 * @summary Verifies IsoChronology.date(TemporalAccessor) delegates to LocalDate.from and returns the ISO date
 * @run main IsoChronologyWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testLocalDateReturnsSameDate();
        testLocalDateTimeReturnsDatePart();
        testLeapDayIsPreserved();
        testTemporalWithoutDateFieldsThrows();
        System.out.println("PASSED");
    }

    static void testLocalDateReturnsSameDate() {
        LocalDate input = LocalDate.of(2019, 6, 15);
        LocalDate result = IsoChronology.INSTANCE.date(input);
        if (!input.equals(result)) {
            throw new AssertionError("Expected " + input + " but got " + result);
        }
    }

    static void testLocalDateTimeReturnsDatePart() {
        LocalDateTime input = LocalDateTime.of(2020, 1, 15, 10, 30, 45);
        LocalDate result = IsoChronology.INSTANCE.date(input);
        LocalDate expected = LocalDate.of(2020, 1, 15);
        if (!expected.equals(result)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testLeapDayIsPreserved() {
        LocalDate result = IsoChronology.INSTANCE.date(LocalDate.of(2024, 2, 29));
        if (result.getDayOfMonth() != 29 || result.getMonthValue() != 2 || result.getYear() != 2024) {
            throw new AssertionError("Leap day not preserved: " + result);
        }
    }

    static void testTemporalWithoutDateFieldsThrows() {
        boolean thrown = false;
        try {
            IsoChronology.INSTANCE.date(LocalTime.NOON);
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected DateTimeException for temporal without date fields");
        }
    }
}
