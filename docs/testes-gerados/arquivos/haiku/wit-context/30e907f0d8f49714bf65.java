/*
 * @test
 * @summary Witup-generated checks for ZoneId.of(String)
 * @run main ZoneIdWitupTest
 */

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ZoneIdWitupTest {

    public static void main(String[] args) {
        testNullThrowsNpe();
        testValidRegionId();
        testOffsetZuluId();
        testNumericOffsetId();
        testUnknownRegionThrows();
    }

    static void testNullThrowsNpe() {
        try {
            ZoneId.of((String) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null zoneId");
    }

    static void testValidRegionId() {
        ZoneId zone = ZoneId.of("Europe/Paris");
        if (!"Europe/Paris".equals(zone.getId())) {
            throw new AssertionError("unexpected id: " + zone.getId());
        }
    }

    static void testOffsetZuluId() {
        ZoneId zone = ZoneId.of("Z");
        if (!ZoneOffset.UTC.equals(zone)) {
            throw new AssertionError("expected ZoneOffset.UTC for 'Z' but got: " + zone);
        }
    }

    static void testNumericOffsetId() {
        ZoneId zone = ZoneId.of("+01:00");
        if (!"+01:00".equals(zone.getId())) {
            throw new AssertionError("unexpected id: " + zone.getId());
        }
    }

    static void testUnknownRegionThrows() {
        try {
            ZoneId.of("Invalid/Zone_WitupUnknown");
        } catch (DateTimeException e) {
            return;
        }
        throw new AssertionError("expected DateTimeException (ZoneRulesException) for unknown region");
    }
}
