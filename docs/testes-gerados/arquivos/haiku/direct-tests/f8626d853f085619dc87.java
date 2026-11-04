/*
 * @test
 * @summary Verifies that ZoneInfoFile.getAliasMap() returns a non-null, unmodifiable and consistent view
 * @modules java.base/sun.util.calendar
 * @run main ZoneInfoFileWitupTest
 */

import java.util.Map;
import sun.util.calendar.ZoneInfoFile;

public class ZoneInfoFileWitupTest {
    public static void main(String[] args) {
        Map<String, String> aliases = ZoneInfoFile.getAliasMap();
        if (aliases == null) {
            throw new RuntimeException("getAliasMap returned null");
        }

        try {
            aliases.put("WITUP_TEST_ALIAS", "UTC");
            throw new RuntimeException("alias map must be unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // expected: Collections.unmodifiableMap wrapper
        }

        if (aliases.containsKey("WITUP_TEST_ALIAS")) {
            throw new RuntimeException("rejected put must not change the map");
        }

        Map<String, String> again = ZoneInfoFile.getAliasMap();
        if (again == null) {
            throw new RuntimeException("second call returned null");
        }
        if (again.size() != aliases.size()) {
            throw new RuntimeException("size differs between calls");
        }
        for (Map.Entry<String, String> e : aliases.entrySet()) {
            if (!e.getValue().equals(again.get(e.getKey()))) {
                throw new RuntimeException("inconsistent alias mapping for " + e.getKey());
            }
        }
    }
}
