/*
 * @test
 * @summary Tests for LocalDateTime.of method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testOfMethodWithValidValues();
        testOfMethodWithEdgeValues();
        testOfMethodWithInvalidValues();
    }

    static void testOfMethodWithValidValues() {
        LocalDateTime result = LocalDateTime.of(2020, 5, 15, 10, 30, 45, 123456789);
        assert result.getYear() == 2020 : "Year mismatch";
        assert result.getMonthValue() == 5 : "Month mismatch";
        assert result.getDayOfMonth() == 15 : "Day mismatch";
        assert result.getHour() == 10 : "Hour mismatch";
        assert result.getMinute() == 30 : "Minute mismatch";
        assert result.getSecond() == 45 : "Second mismatch";
        assert result.getNano() == 123456789 : "Nano mismatch";
    }

    static void testOfMethodWithEdgeValues() {
        LocalDateTime result = LocalDateTime.of(9999, 12, 31, 23, 59, 59, 999999999);
        assert result.getYear() == 9999 : "Year mismatch";
        assert result.getMonthValue() == 12 : "Month mismatch";
        assert result.getDayOfMonth() == 31 : "Day mismatch";
        assert result.getHour() == 23 : "Hour mismatch";
        assert result.getMinute() == 59 : "Minute mismatch";
        assert result.getSecond() == 59 : "Second mismatch";
        assert result.getNano() == 999999999 : "Nano mismatch";
    }

    static void testOfMethodWithInvalidValues() {
        boolean exceptionThrown = false;
        try {
            LocalDateTime.of(2020, 2, 30, 10, 0, 0, 0);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected exception for invalid date";

        exceptionThrown = false;
        try {
            LocalDateTime.of(2020, 1, 1, 25, 0, 0, 0);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected exception for invalid hour";

        exceptionThrown = false;
        try {
            LocalDateTime.of(2020, 1, 1, 0, 0, 60, 0);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected exception for invalid second";

        exceptionThrown = false;
        try {
            LocalDateTime.of(2020, 1, 1, 0, 0, 0, 1000000000);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected exception for invalid nano";
    }
}
