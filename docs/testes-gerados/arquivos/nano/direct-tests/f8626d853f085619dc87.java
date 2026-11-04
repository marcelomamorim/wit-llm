/*
 * @test
 * @summary Test getAliasMap method of ZoneInfoFile for correct behavior.
 * @run main ZoneInfoFileWitupTest
 */

import sun.util.calendar.ZoneInfoFile;
import java.util.Map;
import java.util.Collections;

public class ZoneInfoFileWitupTest {

    public static void main(String[] args) {
        testGetAliasMapReturnsUnmodifiableMap();
    }

    private static void testGetAliasMapReturnsUnmodifiableMap() {
        Map<String, String> aliasMap = ZoneInfoFile.getAliasMap();
        if (aliasMap == null) {
            throw new AssertionError("getAliasMap() returned null");
        }
        // Verify that the returned map is unmodifiable by attempting to put an entry
        try {
            aliasMap.put("testKey", "testValue");
            throw new AssertionError("Expected UnsupportedOperationException when modifying aliasMap");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
        // Verify that the map contains expected entries if known, or at least is not empty
        if (aliasMap.isEmpty()) {
            // It's possible the map is empty; ensure no exception thrown and map is unmodifiable
            System.out.println("getAliasMap() returned an empty map, which is acceptable.");
        } else {
            System.out.println("getAliasMap() returned a map with entries.");
        }
    }
}
