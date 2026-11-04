/*
 * @test
 * @summary Test sun.util.calendar.ZoneInfo.getAliasTable() for expected behavior and null handling
 * @run main ZoneInfoWitupTest
 */

import java.util.Map;
import sun.util.calendar.ZoneInfo;

public class ZoneInfoWitupTest {
    public static void main(String[] args) {
        // Call getAliasTable and verify it returns a non-null Map
        Map<String, String> aliases = ZoneInfo.getAliasTable();
        if (aliases == null) {
            throw new AssertionError("getAliasTable() returned null map");
        }
        // The map should be unmodifiable, so test that by attempting to add an entry and expecting UnsupportedOperationException
        try {
            aliases.put("testKey", "testValue");
            throw new AssertionError("getAliasTable() returned a modifiable map");
        } catch (UnsupportedOperationException expected) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception when modifying alias map: " + e);
        }
    }
}
