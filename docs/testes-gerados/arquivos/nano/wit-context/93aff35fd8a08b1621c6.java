/*
 * @test
 * @summary Tests for java.time.LocalDate.toEpochSecond(LocalTime, ZoneOffset)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testToEpochSecond_NullTime();
        testToEpochSecond_NullOffset();
        testToEpochSecond_ValidInputs();
    }

    private static void testToEpochSecond_NullTime() {
        try {
            LocalDate date = LocalDate.of(2020, 1, 1);
            date.toEpochSecond(null, ZoneOffset.UTC);
            System.out.println("FAIL: Expected NullPointerException for null time");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null time");
        }
    }

    private static void testToEpochSecond_NullOffset() {
        try {
            LocalDate date = LocalDate.of(2020, 1, 1);
            LocalTime time = LocalTime.of(12, 0);
            date.toEpochSecond(time, null);
            System.out.println("FAIL: Expected NullPointerException for null offset");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null offset");
        }
    }

    private static void testToEpochSecond_ValidInputs() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        LocalTime time = LocalTime.of(12, 0);
        ZoneOffset offset = ZoneOffset.UTC;
        long result = date.toEpochSecond(time, offset);
        long expected = date.toEpochDay() * 86400L + time.toSecondOfDay() - offset.getTotalSeconds();
        if (result == expected) {
            System.out.println("PASS: Valid inputs produce expected epoch second");
        } else {
            System.out.println("FAIL: Valid inputs produce unexpected epoch second");
        }
    }
}
