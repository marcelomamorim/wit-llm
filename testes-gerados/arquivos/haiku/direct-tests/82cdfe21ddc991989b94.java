/*
 * @test
 * @summary Verify OffsetDateTime.isEqual compares instant (epoch seconds and nano-of-second) regardless of offset
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        OffsetDateTime a = OffsetDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime b = OffsetDateTime.of(2020, 1, 1, 13, 0, 0, 0, ZoneOffset.ofHours(1));

        check(a.isEqual(b), "same instant with different offset should be equal");
        check(b.isEqual(a), "isEqual should be symmetric");
        check(a.isEqual(a), "isEqual should be reflexive");
        check(!a.equals(b), "equals should differ for different offsets");

        OffsetDateTime nanoDiff = OffsetDateTime.of(2020, 1, 1, 12, 0, 0, 1, ZoneOffset.UTC);
        check(!a.isEqual(nanoDiff), "different nano-of-second should not be equal");

        OffsetDateTime secondDiff = OffsetDateTime.of(2020, 1, 1, 12, 0, 1, 0, ZoneOffset.UTC);
        check(!a.isEqual(secondDiff), "different second should not be equal");

        OffsetDateTime dayDiff = OffsetDateTime.of(2020, 1, 2, 12, 0, 0, 0, ZoneOffset.UTC);
        check(!a.isEqual(dayDiff), "different day should not be equal");

        OffsetDateTime n1 = OffsetDateTime.of(2020, 6, 15, 10, 30, 0, 5, ZoneOffset.ofHours(2));
        OffsetDateTime n2 = OffsetDateTime.of(2020, 6, 15, 8, 30, 0, 5, ZoneOffset.UTC);
        check(n1.isEqual(n2), "equal instants with equal nanos should be equal");

        boolean npe = false;
        try {
            a.isEqual(null);
        } catch (NullPointerException expected) {
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
