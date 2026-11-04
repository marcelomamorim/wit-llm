/*
 * @test
 * @summary Witup-style tests for LocalDate.datesUntil(LocalDate endExclusive)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        basicRangeEndExclusive();
        longRangeCount();
        emptyWhenStartEqualsEnd();
        leapYearRange();
        rejectsReversedRange();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void basicRangeEndExclusive() {
        LocalDate start = LocalDate.of(2018, 1, 30);
        LocalDate end = LocalDate.of(2018, 2, 2);
        List<LocalDate> got = start.datesUntil(end).collect(Collectors.toList());
        List<LocalDate> expected = Arrays.asList(
                LocalDate.of(2018, 1, 30),
                LocalDate.of(2018, 1, 31),
                LocalDate.of(2018, 2, 1));
        check(got.equals(expected), "basic range mismatch: " + got);
    }

    static void longRangeCount() {
        LocalDate start = LocalDate.of(2018, 1, 1);
        LocalDate end = LocalDate.of(2018, 2, 1);
        long count = start.datesUntil(end).count();
        check(count == 31, "expected 31 dates, got " + count);
    }

    static void emptyWhenStartEqualsEnd() {
        LocalDate d = LocalDate.of(2018, 6, 15);
        long count = d.datesUntil(d).count();
        check(count == 0, "expected empty stream, got " + count);
    }

    static void leapYearRange() {
        LocalDate start = LocalDate.of(2016, 2, 28);
        LocalDate end = LocalDate.of(2016, 3, 1);
        List<LocalDate> got = start.datesUntil(end).collect(Collectors.toList());
        List<LocalDate> expected = Arrays.asList(
                LocalDate.of(2016, 2, 28),
                LocalDate.of(2016, 2, 29));
        check(got.equals(expected), "leap year range mismatch: " + got);
    }

    static void rejectsReversedRange() {
        LocalDate start = LocalDate.of(2018, 3, 10);
        LocalDate end = LocalDate.of(2018, 3, 9);
        try {
            start.datesUntil(end);
        } catch (IllegalArgumentException ex) {
            check("2018-03-09 < 2018-03-10".equals(ex.getMessage()),
                  "unexpected message: " + ex.getMessage());
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for reversed range");
    }
}
