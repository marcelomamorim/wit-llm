/*
 * @test
 * @summary Verify LocalDateTime.minusSeconds subtracts seconds correctly across boundaries
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        check(LocalDateTime.of(2020, 1, 1, 0, 0, 0).minusSeconds(1),
              LocalDateTime.of(2019, 12, 31, 23, 59, 59));

        check(LocalDateTime.of(2020, 3, 1, 0, 0, 30).minusSeconds(31),
              LocalDateTime.of(2020, 2, 29, 23, 59, 59));

        LocalDateTime base = LocalDateTime.of(2020, 6, 15, 12, 0, 0);
        check(base.minusSeconds(0), base);

        check(base.minusSeconds(3600),
              LocalDateTime.of(2020, 6, 15, 11, 0, 0));

        check(base.minusSeconds(-5),
              LocalDateTime.of(2020, 6, 15, 12, 0, 5));

        check(LocalDateTime.of(2020, 6, 15, 12, 0, 0, 500000000).minusSeconds(1),
              LocalDateTime.of(2020, 6, 15, 11, 59, 59, 500000000));

        check(LocalDateTime.of(2020, 1, 1, 0, 0, 0).minusSeconds(86400),
              LocalDateTime.of(2019, 12, 31, 0, 0, 0));

        long big = 86400L * 2 + 7;
        check(base.minusSeconds(big).plusSeconds(big), base);

        check(base.minusSeconds(60),
              LocalDateTime.of(2020, 6, 15, 11, 59, 0));
    }

    static void check(LocalDateTime actual, LocalDateTime expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }
}
