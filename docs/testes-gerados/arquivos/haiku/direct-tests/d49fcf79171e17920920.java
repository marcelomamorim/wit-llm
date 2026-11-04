/*
 * @test
 * @summary Verifies LocalDateTime.ofInstant converts instants using the zone rules and rejects null arguments
 * @run main LocalDateTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        check(LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.of("UTC")),
              LocalDateTime.of(1970, 1, 1, 0, 0));

        check(LocalDateTime.ofInstant(Instant.ofEpochSecond(1, 500), ZoneId.of("UTC")),
              LocalDateTime.of(1970, 1, 1, 0, 0, 1, 500));

        check(LocalDateTime.ofInstant(Instant.ofEpochSecond(-1), ZoneId.of("UTC")),
              LocalDateTime.of(1969, 12, 31, 23, 59, 59));

        check(LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.of("+02:00")),
              LocalDateTime.of(1970, 1, 1, 2, 0));

        expectNullPointerException(() -> LocalDateTime.ofInstant(null, ZoneId.of("UTC")));
        expectNullPointerException(() -> LocalDateTime.ofInstant(Instant.EPOCH, null));
    }

    private static void check(LocalDateTime actual, LocalDateTime expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }

    private static void expectNullPointerException(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }
}
