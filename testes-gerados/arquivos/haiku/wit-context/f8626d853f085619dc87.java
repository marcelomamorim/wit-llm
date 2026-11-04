/*
 * @test
 * @summary Verifies public observable behavior of ZoneInfoFile.getAliasMap
 * @modules java.base/sun.util.calendar
 * @run main ZoneInfoFileWitupTest
 */
import java.util.Map;
import sun.util.calendar.ZoneInfoFile;

public class ZoneInfoFileWitupTest {

    public static void main(String[] args) {
        Map<String, String> first = ZoneInfoFile.getAliasMap();
        if (first == null) {
            throw new RuntimeException("getAliasMap returned null");
        }
        for (Map.Entry<String, String> e : first.entrySet()) {
            if (e.getKey() == null || e.getValue() == null || e.getValue().isEmpty()) {
                throw new RuntimeException("Invalid alias entry: " + e);
            }
        }
        try {
            first.put("WITUP_TEST_ALIAS", "UTC");
            throw new RuntimeException("Alias map must be unmodifiable (put accepted)");
        } catch (UnsupportedOperationException expected) {
        }
        Map<String, String> second = ZoneInfoFile.getAliasMap();
        if (second == null) {
            throw new RuntimeException("second getAliasMap call returned null");
        }
        if (!first.equals(second)) {
            throw new RuntimeException("getAliasMap content not stable across calls");
        }
        if (first.size() != second.size()) {
            throw new RuntimeException("getAliasMap size not stable across calls");
        }
        if (second.containsKey("WITUP_TEST_ALIAS")) {
            throw new RuntimeException("Rejected put must not modify the alias map");
        }
    }
}
