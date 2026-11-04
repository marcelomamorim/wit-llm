/*
 * @test
 * @summary Test sun.util.calendar.ZoneInfoFile.getAliasMap() returns unmodifiable map with expected contents
 * @run main ZoneInfoFileWitupTest
 */

import java.util.Map;
import java.util.Collections;

public class ZoneInfoFileWitupTest {
    public static void main(String[] args) throws Exception {
        Map<String, String> aliasMap = sun.util.calendar.ZoneInfoFile.getAliasMap();
        if (aliasMap == null) {
            throw new AssertionError("getAliasMap() returned null");
        }
        // The returned map must be unmodifiable
        try {
            aliasMap.put("testKey", "testValue");
            throw new AssertionError("Returned map should be unmodifiable but put() succeeded");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        // The map should contain at least one entry (aliases is expected to be non-empty)
        if (aliasMap.isEmpty()) {
            throw new AssertionError("Alias map is unexpectedly empty");
        }
        // Check that keys and values are non-null strings
        for (Map.Entry<String, String> e : aliasMap.entrySet()) {
            if (e.getKey() == null) {
                throw new AssertionError("Alias map contains null key");
            }
            if (e.getValue() == null) {
                throw new AssertionError("Alias map contains null value for key: " + e.getKey());
            }
        }
    }
}
