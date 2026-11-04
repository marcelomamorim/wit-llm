/*
 * @test
 * @summary Test sun.util.calendar.ZoneInfoFile.getAliasMap() for NPE and correctness
 * @run main ZoneInfoFileWitupTest
 */

import java.util.Map;
import java.util.Collections;

public class ZoneInfoFileWitupTest {
    public static void main(String[] args) {
        testGetAliasMapNotNull();
    }

    private static void testGetAliasMapNotNull() {
        Map<String, String> map = null;
        try {
            map = sun.util.calendar.ZoneInfoFile.getAliasMap();
        } catch (NullPointerException npe) {
            throw new AssertionError("getAliasMap() threw NullPointerException, expected non-null unmodifiable map", npe);
        }
        if (map == null) {
            throw new AssertionError("getAliasMap() returned null, expected non-null map");
        }
        try {
            map.put("test", "test");
            throw new AssertionError("Returned map should be unmodifiable, but put succeeded");
        } catch (UnsupportedOperationException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception when modifying returned map", e);
        }
    }
}
