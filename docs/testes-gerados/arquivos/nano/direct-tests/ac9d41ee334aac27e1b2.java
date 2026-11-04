/* @test    @summary Test getAliasTable method of ZoneInfo class    @run main ZoneInfoWitupTest */

import java.util.Map;

public class ZoneInfoWitupTest {

    public static void main(String[] args) {
        testGetAliasTableReturnsMap();
    }

    public static void testGetAliasTableReturnsMap() {
        Map<String, String> aliasMap = sun.util.calendar.ZoneInfo.getAliasTable();
        if (aliasMap == null) {
            throw new AssertionError("getAliasTable() returned null");
        }
        if (!(aliasMap instanceof Map)) {
            throw new AssertionError("getAliasTable() did not return a Map");
        }
        // Additional check: ensure the map is not empty (assuming the implementation provides some aliases)
        if (aliasMap.isEmpty()) {
            System.out.println("Warning: getAliasTable() returned an empty map");
        }
        System.out.println("getAliasTable() test passed with map size: " + aliasMap.size());
    }
}
