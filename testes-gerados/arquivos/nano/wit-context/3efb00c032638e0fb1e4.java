/* @test    @summary Test for OffsetDateTime.of(LocalDate, LocalTime, ZoneOffset) method.    @run main OffsetDateTimeWitupTest */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.OffsetDateTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testNullDate();
        testNullTime();
    }

    static void testValidInputs() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        LocalTime time = LocalTime.of(12, 30, 45);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 0);
        OffsetDateTime odt = OffsetDateTime.of(date, time, offset);
        assert odt != null : "OffsetDateTime should not be null for valid inputs";
        assert odt.getYear() == 2023 : "Year mismatch";
        assert odt.getMonthValue() == 3 : "Month mismatch";
        assert odt.getDayOfMonth() == 15 : "Day mismatch";
        assert odt.toLocalTime().getHour() == 12 : "Hour mismatch";
        assert odt.getOffset().equals(offset) : "Offset mismatch";
    }

    static void testNullDate() {
        try {
            LocalTime time = LocalTime.of(12, 0);
            ZoneOffset offset = ZoneOffset.ofHours(1);
            OffsetDateTime.of(null, time, offset);
            assert false : "Expected NullPointerException for null date";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullTime() {
        try {
            LocalDate date = LocalDate.of(2023, 3, 15);
            ZoneOffset offset = ZoneOffset.ofHours(1);
            OffsetDateTime.of(date, null, offset);
            assert false : "Expected NullPointerException for null time";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
