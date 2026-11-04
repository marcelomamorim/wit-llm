/*
 * @test
 * @summary Observable behavior of JapaneseDate.of(JapaneseEra, int, int, int)
 * @run main JapaneseDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.time.temporal.ChronoField;

public class JapaneseDateWitupTest {

    public static void main(String[] args) {
        testHeiseiFirstValidDay();
        testMeijiSixthYearFirstDay();
        testNullEraThrowsNpe();
        testInvalidMonthThrowsDateTimeException();
        testInvalidDayThrowsDateTimeException();
        testDayBeforeEraStartThrowsDateTimeException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectDateTimeException(Runnable r, String message) {
        try {
            r.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void testHeiseiFirstValidDay() {
        JapaneseDate jd = JapaneseDate.of(JapaneseEra.HEISEI, 1, 1, 8);
        check(jd.getEra() == JapaneseEra.HEISEI, "era mismatch");
        check(jd.get(ChronoField.YEAR_OF_ERA) == 1, "year of era mismatch");
        check(jd.get(ChronoField.MONTH_OF_YEAR) == 1, "month mismatch");
        check(jd.get(ChronoField.DAY_OF_MONTH) == 8, "day mismatch");
        check(LocalDate.from(jd).equals(LocalDate.of(1989, 1, 8)), "gregorian date mismatch");
    }

    static void testMeijiSixthYearFirstDay() {
        JapaneseDate jd = JapaneseDate.of(JapaneseEra.MEIJI, 6, 1, 1);
        check(jd.getEra() == JapaneseEra.MEIJI, "era mismatch for Meiji");
        check(jd.get(ChronoField.YEAR_OF_ERA) == 6, "year of era mismatch for Meiji");
        check(LocalDate.from(jd).equals(LocalDate.of(1873, 1, 1)), "gregorian date mismatch for Meiji");
    }

    static void testNullEraThrowsNpe() {
        boolean thrown = false;
        try {
            JapaneseDate.of(null, 1, 1, 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null era must throw NullPointerException");
    }

    static void testInvalidMonthThrowsDateTimeException() {
        expectDateTimeException(
            () -> JapaneseDate.of(JapaneseEra.HEISEI, 1, 13, 1),
            "month 13 must be rejected");
    }

    static void testInvalidDayThrowsDateTimeException() {
        expectDateTimeException(
            () -> JapaneseDate.of(JapaneseEra.HEISEI, 1, 1, 32),
            "day 32 must be rejected");
    }

    static void testDayBeforeEraStartThrowsDateTimeException() {
        expectDateTimeException(
            () -> JapaneseDate.of(JapaneseEra.HEISEI, 1, 1, 7),
            "date before Heisei start must be rejected");
    }
}
