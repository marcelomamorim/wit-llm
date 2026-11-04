/*
 * @test
 * @summary Checks public behavior of ZoneInfo.getAliasTable(): non-null, stable, and with non-null keys and values
 * @modules java.base/sun.util.calendar
 * @run main ZoneInfoWitupTest
 */

import java.util.Map;
import sun.util.calendar.ZoneInfo;

public class ZoneInfoWitupTest {

    public static void main(String[] args) throws Exception {
        Map<String, String> first = ZoneInfo.getAliasTable();
        if (first == null) {
            throw new RuntimeException("getAliasTable() returned null");
        }

        Map<String, String> second = ZoneInfo.getAliasTable();
        if (second == null) {
            throw new RuntimeException("second getAliasTable() call returned null");
        }

        if (first.size() != second.size()) {
            throw new RuntimeException("alias table size differs between calls: "
                    + first.size() + " vs " + second.size());
        }

        for (Map.Entry<String, String> e : first.entrySet()) {
            if (e.getKey() == null) {
                throw new RuntimeException("alias table contains a null key");
            }
            if (e.getValue() == null) {
                throw new RuntimeException("alias table contains a null value for key " + e.getKey());
            }
            String other = second.get(e.getKey());
            if (!e.getValue().equals(other)) {
                throw new RuntimeException("alias mapping differs between calls for key " + e.getKey());
            }
        }

        if (first.get("__witup_unknown_alias__") != null) {
            throw new RuntimeException("unknown alias unexpectedly mapped to a value");
        }
    }
}
