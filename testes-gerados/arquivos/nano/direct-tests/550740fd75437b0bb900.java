/*
 * @test
 * @summary Test OffsetTime.atDate(LocalDate) method for correct behavior.
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.OffsetDateTime;
import java.time.LocalDate;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testAtDateWithValidDate();
        testAtDateWithDifferentTimes();
        testAtDateWithDifferentOffsets();
    }

    static void testAtDateWithValidDate() {
        OffsetTime offsetTime = OffsetTime.of(10, 15, 30, 0, java.time.ZoneOffset.ofHours(2));
        LocalDate date = LocalDate.of(2023, 3, 15);
        OffsetDateTime result = offsetTime.atDate(date);
        assert result.getDate().equals(date) : "Expected date to be " + date;
        assert result.getTime().equals(offsetTime.toLocalTime()) : "Expected time to be " + offsetTime.toLocalTime();
        assert result.getOffset().equals(offsetTime.getOffset()) : "Expected offset to be " + offsetTime.getOffset();
    }

    static void testAtDateWithDifferentTimes() {
        OffsetTime offsetTime1 = OffsetTime.of(0, 0, 0, 0, java.time.ZoneOffset.ofHours(-5));
        LocalDate date1 = LocalDate.of(2022, 12, 31);
        OffsetDateTime result1 = offsetTime1.atDate(date1);
        assert result1.getDate().equals(date1) : "Expected date to be " + date1;
        assert result1.getTime().equals(offsetTime1.toLocalTime()) : "Expected time to be " + offsetTime1.toLocalTime();
        assert result1.getOffset().equals(offsetTime1.getOffset()) : "Expected offset to be " + offsetTime1.getOffset();

        OffsetTime offsetTime2 = OffsetTime.of(23, 59, 59, 999999999, java.time.ZoneOffset.ofHours(0));
        LocalDate date2 = LocalDate.of(2021, 1, 1);
        OffsetDateTime result2 = offsetTime2.atDate(date2);
        assert result2.getDate().equals(date2) : "Expected date to be " + date2;
        assert result2.getTime().equals(offsetTime2.toLocalTime()) : "Expected time to be " + offsetTime2.toLocalTime();
        assert result2.getOffset().equals(offsetTime2.getOffset()) : "Expected offset to be " + offsetTime2.getOffset();
    }

    static void testAtDateWithDifferentOffsets() {
        OffsetTime offsetTime = OffsetTime.of(12, 0, 0, 0, java.time.ZoneOffset.ofHours(3));
        LocalDate date = LocalDate.of(2024, 6, 1);
        OffsetDateTime result = offsetTime.atDate(date);
        assert result.getDate().equals(date) : "Expected date to be " + date;
        assert result.getTime().equals(offsetTime.toLocalTime()) : "Expected time to be " + offsetTime.toLocalTime();
        assert result.getOffset().equals(offsetTime.getOffset()) : "Expected offset to be " + offsetTime.getOffset();
    }
}
