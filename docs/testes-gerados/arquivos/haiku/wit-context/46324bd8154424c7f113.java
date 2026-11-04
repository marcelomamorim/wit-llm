/*
 * @test
 * @summary Witup-generated checks for ZoneId.of(String, Map) null guards and alias resolution
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class ZoneIdWitupTest {

    public static void main(String[] args) {
        testNullZoneIdThrowsNpe();
        testNullAliasMapThrowsNpe();
        testBothNullThrowsNpe();
        testAliasResolvesToRegion();
        testUnmatchedIdFallsBackToOriginal();
        testAliasResolvesToOffset();
    }

    static void testNullZoneIdThrowsNpe() {
        Map<String, String> aliases = new HashMap<>();
        expectNpe(null, aliases, "null zoneId with non-null aliasMap");
    }

    static void testNullAliasMapThrowsNpe() {
        expectNpe("Europe/Paris", null, "non-null zoneId with null aliasMap");
    }

    static void testBothNullThrowsNpe() {
        expectNpe(null, null, "null zoneId and null aliasMap");
    }

    static void testAliasResolvesToRegion() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("EST", "America/New_York");
        ZoneId z = ZoneId.of("EST", aliases);
        check("America/New_York".equals(z.getId()),
              "alias EST should resolve to America/New_York but was " + z.getId());
    }

    static void testUnmatchedIdFallsBackToOriginal() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("EST", "America/New_York");
        ZoneId z = ZoneId.of("Europe/Paris", aliases);
        check("Europe/Paris".equals(z.getId()),
              "unmatched id should be kept but was " + z.getId());
    }

    static void testAliasResolvesToOffset() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("OFF", "+01:00");
        ZoneId z = ZoneId.of("OFF", aliases);
        check("+01:00".equals(z.getId()),
              "alias OFF should resolve to +01:00 but was " + z.getId());
    }

    static void expectNpe(String zoneId, Map<String, String> aliases, String description) {
        boolean thrown = false;
        try {
            ZoneId.of(zoneId, aliases);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for " + description);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
