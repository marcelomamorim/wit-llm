/*
 * @test
 * @summary Verify that the unmodifiable map wrapper's remove(key, value) throws UnsupportedOperationException and leaves the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, String> base = new HashMap<>();
        base.put("a", "1");
        Map<String, String> unmodifiable = Collections.unmodifiableMap(base);

        boolean thrownForPresentEntry = false;
        try {
            unmodifiable.remove("a", "1");
        } catch (UnsupportedOperationException e) {
            thrownForPresentEntry = true;
        }
        if (!thrownForPresentEntry) {
            throw new AssertionError("Expected UnsupportedOperationException for present entry");
        }

        boolean thrownForAbsentEntry = false;
        try {
            unmodifiable.remove("missing", "x");
        } catch (UnsupportedOperationException e) {
            thrownForAbsentEntry = true;
        }
        if (!thrownForAbsentEntry) {
            throw new AssertionError("Expected UnsupportedOperationException for absent entry");
        }

        if (!"1".equals(base.get("a")) || base.size() != 1) {
            throw new AssertionError("Backing map was modified");
        }
    }
}
