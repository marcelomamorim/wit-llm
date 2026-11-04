/*
 * @test
 * @summary Witup-generated checks for OffsetDateTime.isEqual
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        OffsetDateTime utc = OffsetDateTime.of(2018, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime plus2 = OffsetDateTime.of(2018, 1, 1, 14, 0, 0, 0, ZoneOffset.ofHours(2));
        check(utc.isEqual(plus2), "same instant with different offset should be equal");
        check(plus2.isEqual(utc), "isEqual should be symmetric for same instant");
        check(utc.isEqual(utc), "isEqual should be reflexive");

        OffsetDateTime oneNano = OffsetDateTime.of(2018, 1, 1, 12, 0, 0, 1, ZoneOffset.UTC);
        check(!utc.isEqual(oneNano), "different nano of second should not be equal");

        OffsetDateTime laterSecond = OffsetDateTime.of(2018, 1, 1, 12, 0, 1, 0, ZoneOffset.UTC);
        check(!utc.isEqual(laterSecond), "different epoch second should not be equal");

        boolean npe = false;
        try {
            utc.isEqual(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "isEqual(null) should throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
