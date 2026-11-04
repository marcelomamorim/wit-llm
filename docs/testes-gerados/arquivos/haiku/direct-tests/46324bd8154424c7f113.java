/*
 * @test
 * @summary Tests ZoneId.of(String, Map) alias resolution and exception behavior
 * @run main ZoneIdWitupTest
 */

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class ZoneIdWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static void testAliasResolution() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("MyZone", "Europe/Paris");
        ZoneId z = ZoneId.of("MyZone", aliases);
        check("Europe/Paris".equals(z.getId()), "alias should resolve to Europe/Paris, got " + z.getId());
    }

    private static void testNonAliasPassThrough() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("MyZone", "Europe/Paris");
        ZoneId z = ZoneId.of("UTC", aliases);
        check("UTC".equals(z.getId()), "non-alias id should pass through, got " + z.getId());
    }

    private static void testOffsetIdWithAliasMap() {
        Map<String, String> aliases = new HashMap<>();
        ZoneId z = ZoneId.of("Z", aliases);
        check(ZoneOffset.UTC.equals(z), "\"Z\" should resolve to ZoneOffset.UTC, got " + z);
    }

    private static void testNullMappedValueFallsBackToOriginal() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("Europe/Paris", null);
        ZoneId z = ZoneId.of("Europe/Paris", aliases);
        check("Europe/Paris".equals(z.getId()), "null alias value should fall back to input, got " + z.getId());
    }

    private static void testNullZoneIdThrowsNpe() {
        Map<String, String> aliases = new HashMap<>();
        try {
            ZoneId.of((String) null, aliases);
            check(false, "null zoneId should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullAliasMapThrowsNpe() {
        try {
            ZoneId.of("UTC", null);
            check(false, "null aliasMap should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testUnknownIdThrowsDateTimeException() {
        Map<String, String> aliases = new HashMap<>();
        try {
            ZoneId.of("Mars/Olympus", aliases);
            check(false, "unknown zone id should throw DateTimeException");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void testAliasToInvalidIdThrowsDateTimeException() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("Bad", "Mars/Olympus");
        try {
            ZoneId.of("Bad", aliases);
            check(false, "alias to unknown zone id should throw DateTimeException");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testAliasResolution();
        testNonAliasPassThrough();
        testOffsetIdWithAliasMap();
        testNullMappedValueFallsBackToOriginal();
        testNullZoneIdThrowsNpe();
        testNullAliasMapThrowsNpe();
        testUnknownIdThrowsDateTimeException();
        testAliasToInvalidIdThrowsDateTimeException();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
