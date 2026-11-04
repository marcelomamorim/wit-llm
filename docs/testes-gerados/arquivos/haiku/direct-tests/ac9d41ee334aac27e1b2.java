/*
 * @test
 * @summary Verify ZoneInfo.getAliasTable() returns a usable, consistent alias map
 * @modules java.base/sun.util.calendar
 * @run main ZoneInfoWitupTest
 */

import java.util.Map;
import sun.util.calendar.ZoneInfo;

public class ZoneInfoWitupTest {

    public static void main(String[] args) {
        testNonNullAndNonEmpty();
        testEntriesAreNonNullAndNonEmpty();
        testRepeatedCallsAreEquivalent();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNonNullAndNonEmpty() {
        Map<String, String> aliases = ZoneInfo.getAliasTable();
        check(aliases != null, "getAliasTable() returned null");
        check(!aliases.isEmpty(), "getAliasTable() returned an empty map");
    }

    static void testEntriesAreNonNullAndNonEmpty() {
        Map<String, String> aliases = ZoneInfo.getAliasTable();
        for (Map.Entry<String, String> e : aliases.entrySet()) {
            check(e.getKey() != null && !e.getKey().isEmpty(), "alias key is null or empty");
            check(e.getValue() != null && !e.getValue().isEmpty(), "alias value is null or empty for key " + e.getKey());
        }
    }

    static void testRepeatedCallsAreEquivalent() {
        Map<String, String> first = ZoneInfo.getAliasTable();
        Map<String, String> second = ZoneInfo.getAliasTable();
        check(first.equals(second), "repeated calls to getAliasTable() returned different contents");
    }
}
