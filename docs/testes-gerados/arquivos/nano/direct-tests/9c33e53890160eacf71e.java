/*
 * @test
 * @summary Test LocalDateTime.of(LocalDate, LocalTime) method for correctness.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testOfMethodWithValidInputs();
        testOfMethodWithNullDate();
        testOfMethodWithNullTime();
    }

    static void testOfMethodWithValidInputs() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        LocalTime time = LocalTime.of(14, 30, 45);
        LocalDateTime result = LocalDateTime.of(date, time);
        assert result.getYear() == 2023 : "Year mismatch";
        assert result.getMonthValue() == 3 : "Month mismatch";
        assert result.getDayOfMonth() == 15 : "Day mismatch";
        assert result.getHour() == 14 : "Hour mismatch";
        assert result.getMinute() == 30 : "Minute mismatch";
        assert result.getSecond() == 45 : "Second mismatch";
    }

    static void testOfMethodWithNullDate() {
        LocalTime time = LocalTime.of(10, 20);
        try {
            LocalDateTime.of(null, time);
            assert false : "Expected NullPointerException for null date";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfMethodWithNullTime() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        try {
            LocalDateTime.of(date, null);
            assert false : "Expected NullPointerException for null time";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
