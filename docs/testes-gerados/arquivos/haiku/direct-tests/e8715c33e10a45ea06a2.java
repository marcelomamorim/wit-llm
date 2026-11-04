/*
 * @test
 * @summary LocalDateTime.withNano preserves other fields, accepts boundary nano values and rejects out-of-range values
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testPreservesDateAndOtherTimeFields();
        testBoundaryValues();
        testImmutability();
        testInvalidNanoThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPreservesDateAndOtherTimeFields() {
        LocalDateTime base = LocalDateTime.of(2020, 1, 2, 3, 4, 5, 999);
        LocalDateTime result = base.withNano(123);
        check(result.getYear() == 2020, "year changed");
        check(result.getMonthValue() == 1, "month changed");
        check(result.getDayOfMonth() == 2, "day changed");
        check(result.getHour() == 3, "hour changed");
        check(result.getMinute() == 4, "minute changed");
        check(result.getSecond() == 5, "second changed");
        check(result.getNano() == 123, "nano not updated: " + result.getNano());
        check(result.equals(LocalDateTime.of(2020, 1, 2, 3, 4, 5, 123)), "unexpected result");
    }

    static void testBoundaryValues() {
        LocalDateTime base = LocalDateTime.of(2021, 6, 15, 12, 30, 45, 500);
        check(base.withNano(0).getNano() == 0, "nano 0 not applied");
        check(base.withNano(0).equals(LocalDateTime.of(2021, 6, 15, 12, 30, 45, 0)),
              "nano 0 result mismatch");
        check(base.withNano(999_999_999).getNano() == 999_999_999, "max nano not applied");
        check(base.withNano(999_999_999).equals(
                LocalDateTime.of(2021, 6, 15, 12, 30, 45, 999_999_999)),
              "max nano result mismatch");
    }

    static void testImmutability() {
        LocalDateTime base = LocalDateTime.of(2020, 1, 2, 3, 4, 5, 999);
        LocalDateTime result = base.withNano(1);
        check(result.getNano() == 1, "result nano incorrect");
        check(base.getNano() == 999, "original instance was modified");
    }

    static void expectDateTimeException(LocalDateTime base, int nano) {
        try {
            base.withNano(nano);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for nano " + nano);
    }

    static void testInvalidNanoThrows() {
        LocalDateTime base = LocalDateTime.of(2020, 1, 2, 3, 4, 5, 999);
        expectDateTimeException(base, -1);
        expectDateTimeException(base, 1_000_000_000);
    }
}
