/*
 * @test
 * @summary Test OffsetTime.atDate(LocalDate) method
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testAtDateBasic();
        testAtDateEpochDay();
    }

    private static void testAtDateBasic() {
        OffsetTime ot = OffsetTime.of(10, 20, 30, 40, ZoneOffset.ofHours(2));
        LocalDate date = LocalDate.of(2018, 9, 1);
        OffsetDateTime odt = ot.atDate(date);
        if (!odt.toLocalDate().equals(date)) {
            throw new AssertionError("Date part mismatch: " + odt.toLocalDate() + " != " + date);
        }
        if (!odt.toLocalTime().equals(ot.toLocalTime())) {
            throw new AssertionError("Time part mismatch: " + odt.toLocalTime() + " != " + ot.toLocalTime());
        }
        if (!odt.getOffset().equals(ot.getOffset())) {
            throw new AssertionError("Offset mismatch: " + odt.getOffset() + " != " + ot.getOffset());
        }
    }

    private static void testAtDateEpochDay() {
        OffsetTime ot = OffsetTime.of(0, 0, 0, 0, ZoneOffset.UTC);
        LocalDate date = LocalDate.ofEpochDay(0); // 1970-01-01
        OffsetDateTime odt = ot.atDate(date);
        if (!odt.toLocalDate().equals(date)) {
            throw new AssertionError("Date part mismatch for epoch day: " + odt.toLocalDate() + " != " + date);
        }
        if (!odt.toLocalTime().equals(ot.toLocalTime())) {
            throw new AssertionError("Time part mismatch for epoch day: " + odt.toLocalTime() + " != " + ot.toLocalTime());
        }
        if (!odt.getOffset().equals(ot.getOffset())) {
            throw new AssertionError("Offset mismatch for epoch day: " + odt.getOffset() + " != " + ot.getOffset());
        }
    }
}
