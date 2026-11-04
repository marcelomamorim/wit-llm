/*
 * @test
 * @summary LocalTime.ofInstant converts instants to local time using zone offsets, nanos and null checks
 * @run main LocalTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LocalTimeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkNpe(Runnable r, String message) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    public static void main(String[] args) {
        checkNpe(() -> LocalTime.ofInstant(null, ZoneId.of("UTC")),
                "null instant must throw NullPointerException");
        checkNpe(() -> LocalTime.ofInstant(Instant.EPOCH, null),
                "null zone must throw NullPointerException");

        LocalTime utcMidnight = LocalTime.ofInstant(Instant.EPOCH, ZoneId.of("UTC"));
        check(utcMidnight.equals(LocalTime.of(0, 0)),
                "epoch in UTC should be 00:00 but was " + utcMidnight);

        LocalTime plusTwo = LocalTime.ofInstant(Instant.EPOCH, ZoneOffset.ofHours(2));
        check(plusTwo.equals(LocalTime.of(2, 0)),
                "epoch with +02:00 should be 02:00 but was " + plusTwo);

        LocalTime minusOne = LocalTime.ofInstant(Instant.EPOCH, ZoneOffset.ofHours(-1));
        check(minusOne.equals(LocalTime.of(23, 0)),
                "epoch with -01:00 should wrap to 23:00 but was " + minusOne);

        LocalTime secondOffset = LocalTime.ofInstant(Instant.EPOCH, ZoneOffset.ofTotalSeconds(30));
        check(secondOffset.equals(LocalTime.of(0, 0, 30)),
                "epoch with +00:00:30 should be 00:00:30 but was " + secondOffset);

        LocalTime nanos = LocalTime.ofInstant(Instant.ofEpochSecond(0, 123456789L), ZoneId.of("UTC"));
        check(nanos.equals(LocalTime.of(0, 0, 0, 123456789)),
                "nanos must be preserved but was " + nanos);

        LocalTime wrap = LocalTime.ofInstant(Instant.ofEpochSecond(86400L + 5L), ZoneId.of("UTC"));
        check(wrap.equals(LocalTime.of(0, 0, 5)),
                "one day plus 5s should be 00:00:05 but was " + wrap);

        LocalTime negativeEpoch = LocalTime.ofInstant(Instant.ofEpochSecond(-1L), ZoneId.of("UTC"));
        check(negativeEpoch.equals(LocalTime.of(23, 59, 59)),
                "epoch -1s should be 23:59:59 but was " + negativeEpoch);
    }
}
