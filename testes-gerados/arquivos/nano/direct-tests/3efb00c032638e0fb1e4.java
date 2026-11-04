/* @test    @summary Test OffsetDateTime.of(LocalDate, LocalTime, ZoneOffset) for correctness and regressions    @run main OffsetDateTimeWitupTest */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testOfMethodWithValidInputs();
        testOfMethodWithDifferentOffsets();
        testOfMethodWithNullInputs();
    }

    static void testOfMethodWithValidInputs() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        LocalTime time = LocalTime.of(12, 30, 45);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 0);
        OffsetDateTime odt = OffsetDateTime.of(date, time, offset);
        assert odt != null : "OffsetDateTime should not be null";
        assert odt.getYear() == 2023 : "Year mismatch";
        assert odt.getMonthValue() == 3 : "Month mismatch";
        assert odt.getDayOfMonth() == 15 : "Day mismatch";
        assert odt.toLocalTime().getHour() == 12 : "Hour mismatch";
        assert odt.getOffset().equals(offset) : "Offset mismatch";
    }

    static void testOfMethodWithDifferentOffsets() {
        LocalDate date = LocalDate.of(2022, 12, 31);
        LocalTime time = LocalTime.of(23, 59, 59);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(-5, -30);
        OffsetDateTime odt = OffsetDateTime.of(date, time, offset);
        assert odt != null : "OffsetDateTime should not be null";
        assert odt.getYear() == 2022 : "Year mismatch";
        assert odt.getMonthValue() == 12 : "Month mismatch";
        assert odt.getDayOfMonth() == 31 : "Day mismatch";
        assert odt.toLocalTime().getHour() == 23 : "Hour mismatch";
        assert odt.getOffset().equals(offset) : "Offset mismatch";
    }

    static void testOfMethodWithNullInputs() {
        try {
            OffsetDateTime.of(null, LocalTime.of(10, 0), ZoneOffset.UTC);
            assert false : "Expected NullPointerException for null date";
        } catch (NullPointerException e) {
            // expected
        }
        try {
            OffsetDateTime.of(LocalDate.of(2023, 1, 1), null, ZoneOffset.UTC);
            assert false : "Expected NullPointerException for null time";
        } catch (NullPointerException e) {
            // expected
        }
        try {
            OffsetDateTime.of(LocalDate.of(2023, 1, 1), LocalTime.of(10, 0), null);
            assert false : "Expected NullPointerException for null offset";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
