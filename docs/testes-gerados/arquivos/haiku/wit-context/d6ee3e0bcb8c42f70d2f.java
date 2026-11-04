/*
 * @test
 * @summary Witup-derived checks for LocalDate.of(int,int,int) argument validation
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        checkValid(2000, 2, 29);
        checkValid(2024, 12, 31);
        checkValid(1, 1, 1);
        checkValid(-999999999, 1, 1);
        checkValid(999999999, 12, 31);

        expectInvalid(1000000000, 1, 1);
        expectInvalid(-1000000000, 1, 1);
        expectInvalid(2000, 0, 1);
        expectInvalid(2000, 13, 1);
        expectInvalid(2000, -1, 1);
        expectInvalid(2000, 1, 0);
        expectInvalid(2000, 1, 32);
        expectInvalid(2000, 4, 31);
        expectInvalid(2001, 2, 29);
        expectInvalid(2001, 2, 0);
    }

    private static void checkValid(int year, int month, int day) {
        LocalDate d = LocalDate.of(year, month, day);
        if (d.getYear() != year) {
            throw new RuntimeException("year mismatch for " + year + "-" + month + "-" + day);
        }
        if (d.getMonthValue() != month) {
            throw new RuntimeException("month mismatch for " + year + "-" + month + "-" + day);
        }
        if (d.getDayOfMonth() != day) {
            throw new RuntimeException("day mismatch for " + year + "-" + month + "-" + day);
        }
    }

    private static void expectInvalid(int year, int month, int day) {
        try {
            LocalDate d = LocalDate.of(year, month, day);
            throw new RuntimeException("expected DateTimeException for " + year + "," + month + "," + day + " but got " + d);
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
