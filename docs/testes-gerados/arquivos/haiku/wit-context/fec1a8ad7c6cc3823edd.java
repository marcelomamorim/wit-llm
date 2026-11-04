/*
 * @test
 * @summary Verify that Collections.emptyMap().putIfAbsent always throws UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        Map<String, String> empty = Collections.emptyMap();

        expectUnsupported(() -> empty.putIfAbsent("k", "v"));
        expectUnsupported(() -> empty.putIfAbsent(null, null));
        expectUnsupported(() -> empty.putIfAbsent("k", null));

        if (!empty.isEmpty()) {
            throw new RuntimeException("emptyMap changed after putIfAbsent");
        }
        if (empty.get("k") != null) {
            throw new RuntimeException("emptyMap contains key after putIfAbsent");
        }
    }

    private static void expectUnsupported(Runnable action) {
        try {
            action.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("UnsupportedOperationException not thrown");
    }
}
