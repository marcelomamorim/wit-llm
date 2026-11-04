/*
 * @test
 * @summary Test sun.util.calendar.ZoneInfo.getAliasTable() returns non-null map
 * @run main ZoneInfoWitupTest
 */

import java.util.Map;
import sun.util.calendar.ZoneInfo;

public class ZoneInfoWitupTest {
    public static void main(String[] args) {
        Map<String, String> aliasMap = ZoneInfo.getAliasTable();
        if (aliasMap == null) {
            throw new RuntimeException("getAliasTable() returned null");
        }
        // Check that the map is not empty (expected to have some aliases)
        if (aliasMap.isEmpty()) {
            throw new RuntimeException("getAliasTable() returned an empty map");
        }
        // Check that keys and values are non-null strings
        for (Map.Entry<String, String> entry : aliasMap.entrySet()) {
            if (entry.getKey() == null) {
                throw new RuntimeException("Alias map contains null key");
            }
            if (entry.getValue() == null) {
                throw new RuntimeException("Alias map contains null value for key: " + entry.getKey());
            }
        }
        System.out.println("ZoneInfo.getAliasTable() test passed. Aliases count: " + aliasMap.size());
    }
}
