/*
 * @test
 * @summary Witup-derived checks for LocalDate.minusMonths observable results and out-of-range DateTimeException
 * @run main LocalDateWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        check(LocalDate.of(2020, 3, 15).minusMonths(0), LocalDate.of(2020, 3, 15));
        check(LocalDate.of(2020, 3, 15).minusMonths(1), LocalDate.of(2020, 2, 15));
        check(LocalDate.of(2020, 3, 15).minusMonths(12), LocalDate.of(2019, 3, 15));
        check(LocalDate.of(2020, 3, 15).minusMonths(-1), LocalDate.of(2020, 4, 15));
        check(LocalDate.of(2020, 1, 31).minusMonths(1), LocalDate.of(2019, 12, 31));
        check(LocalDate.of(2020, 3, 31).minusMonths(1), LocalDate.of(2020, 2, 29));
        check(LocalDate.of(2019, 3, 31).minusMonths(1), LocalDate.of(2019, 2, 28));
        expectDateTimeException(LocalDate.of(2020, 3, 15), 24000000000L);
        expectDateTimeException(LocalDate.of(2020, 3, 15), -24000000000L);
    }

    static void check(LocalDate actual, LocalDate expected) {
        if (!actual.equals(expected)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }

    static void expectDateTimeException(LocalDate date, long months) {
        try {
            LocalDate result = date.minusMonths(months);
            throw new RuntimeException("Expected DateTimeException but got " + result);
        } catch (DateTimeException e) {
            return;
        }
    }
}
