/*
 * @test
 * @summary Tests the toEpochSecond(LocalDate) method of OffsetTime for various scenarios.
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testToEpochSecondWithZeroOffset();
        testToEpochSecondWithPositiveOffset();
        testToEpochSecondWithNegativeOffset();
        testToEpochSecondWithMaxOffset();
        testToEpochSecondWithMinOffset();
    }

    static void testToEpochSecondWithZeroOffset() {
        OffsetTime time = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC);
        LocalDate date = LocalDate.of(2023, 1, 1);
        long epochSecond = time.toEpochSecond(date);
        long expected = (date.toEpochDay() * 86400L) + time.toSecondOfDay();
        expected -= ZoneOffset.UTC.getTotalSeconds();
        assert epochSecond == expected : "Failed: Zero offset";
    }

    static void testToEpochSecondWithPositiveOffset() {
        OffsetTime time = OffsetTime.of(15, 30, 0, 0, ZoneOffset.ofHours(5));
        LocalDate date = LocalDate.of(2023, 6, 15);
        long epochSecond = time.toEpochSecond(date);
        long expected = (date.toEpochDay() * 86400L) + time.toSecondOfDay();
        expected -= ZoneOffset.ofHours(5).getTotalSeconds();
        assert epochSecond == expected : "Failed: Positive offset";
    }

    static void testToEpochSecondWithNegativeOffset() {
        OffsetTime time = OffsetTime.of(8, 45, 0, 0, ZoneOffset.ofHours(-3));
        LocalDate date = LocalDate.of(2022, 12, 31);
        long epochSecond = time.toEpochSecond(date);
        long expected = (date.toEpochDay() * 86400L) + time.toSecondOfDay();
        expected -= ZoneOffset.ofHours(-3).getTotalSeconds();
        assert epochSecond == expected : "Failed: Negative offset";
    }

    static void testToEpochSecondWithMaxOffset() {
        ZoneOffset maxOffset = ZoneOffset.ofTotalSeconds(64800); // +18:00
        OffsetTime time = OffsetTime.of(23, 59, 59, 999999999, maxOffset);
        LocalDate date = LocalDate.of(2023, 1, 1);
        long epochSecond = time.toEpochSecond(date);
        long expected = (date.toEpochDay() * 86400L) + time.toSecondOfDay();
        expected -= maxOffset.getTotalSeconds();
        assert epochSecond == expected : "Failed: Max offset";
    }

    static void testToEpochSecondWithMinOffset() {
        ZoneOffset minOffset = ZoneOffset.ofTotalSeconds(-64800); // -18:00
        OffsetTime time = OffsetTime.of(0, 0, 0, 0, minOffset);
        LocalDate date = LocalDate.of(2023, 1, 1);
        long epochSecond = time.toEpochSecond(date);
        long expected = (date.toEpochDay() * 86400L) + time.toSecondOfDay();
        expected -= minOffset.getTotalSeconds();
        assert epochSecond == expected : "Failed: Min offset";
    }
}
