/* @test    @summary Test OffsetDateTime.of(LocalDateTime, ZoneOffset) method for correctness and regressions    @run main OffsetDateTimeWitupTest */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.OffsetDateTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testOfMethodWithValidInputs();
        testOfMethodWithDifferentDateTimes();
        testOfMethodWithDifferentOffsets();
    }

    static void testOfMethodWithValidInputs() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 30, 45, 123456789);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(5, 30);
        OffsetDateTime odt = OffsetDateTime.of(dateTime, offset);
        assert odt != null : "OffsetDateTime.of should not return null";
        assert odt.getYear() == 2023 : "Year should be 2023";
        assert odt.getMonthValue() == 3 : "Month should be 3";
        assert odt.getDayOfMonth() == 15 : "Day of month should be 15";
        assert odt.getHour() == 12 : "Hour should be 12";
        assert odt.getMinute() == 30 : "Minute should be 30";
        assert odt.getSecond() == 45 : "Second should be 45";
        assert odt.getNano() == 123456789 : "Nano should be 123456789";
        assert odt.getOffset().equals(offset) : "Offset should match input";
    }

    static void testOfMethodWithDifferentDateTimes() {
        LocalDateTime dateTime1 = LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(1999, 12, 31, 23, 59, 59, 999999999);
        ZoneOffset offset = ZoneOffset.ofHours(0);
        OffsetDateTime odt1 = OffsetDateTime.of(dateTime1, offset);
        OffsetDateTime odt2 = OffsetDateTime.of(dateTime2, offset);
        assert odt1 != null && odt2 != null : "OffsetDateTime.of should not return null";
        assert odt1.getYear() == 2000 : "Year should be 2000";
        assert odt2.getYear() == 1999 : "Year should be 1999";
        assert odt1.getMonthValue() == 1 : "Month should be 1";
        assert odt2.getMonthValue() == 12 : "Month should be 12";
        assert odt1.getDayOfMonth() == 1 : "Day should be 1";
        assert odt2.getDayOfMonth() == 31 : "Day should be 31";
    }

    static void testOfMethodWithDifferentOffsets() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 12, 0);
        ZoneOffset offset1 = ZoneOffset.ofHours(0);
        ZoneOffset offset2 = ZoneOffset.ofHoursMinutes(3, 0);
        OffsetDateTime odt1 = OffsetDateTime.of(dateTime, offset1);
        OffsetDateTime odt2 = OffsetDateTime.of(dateTime, offset2);
        assert odt1 != null && odt2 != null : "OffsetDateTime.of should not return null";
        assert odt1.getOffset().equals(offset1) : "Offset should match offset1";
        assert odt2.getOffset().equals(offset2) : "Offset should match offset2";
        assert odt1.toEpochSecond() != odt2.toEpochSecond() : "Epoch seconds should differ for different offsets";
    }
}
