/*
 * @test
 * @summary Test for LocalDateTime.of(LocalDate, LocalTime) method, focusing on exception paths.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testNullDate();
        testNullTime();
    }

    private static void testNullDate() {
        try {
            LocalTime time = LocalTime.of(12, 0);
            LocalDateTime.of(null, time);
            System.err.println("Failed: Expected NullPointerException for null date");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null date");
        }
    }

    private static void testNullTime() {
        try {
            LocalDate date = LocalDate.of(2020, 1, 1);
            LocalDateTime.of(date, null);
            System.err.println("Failed: Expected NullPointerException for null time");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null time");
        }
    }
}
