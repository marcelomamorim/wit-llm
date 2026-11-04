/*
 * @test
 * @summary Verify that replace on an unmodifiable map view throws UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Map<String, Integer> view = Collections.unmodifiableMap(base);

        expectUnsupported(() -> view.replace("a", 2));
        if (!Integer.valueOf(1).equals(view.get("a"))) {
            throw new RuntimeException("Value changed after rejected replace of existing key");
        }

        expectUnsupported(() -> view.replace("missing", 3));
        if (view.containsKey("missing")) {
            throw new RuntimeException("Key inserted after rejected replace of absent key");
        }

        expectUnsupported(() -> view.replace(null, 4));
        if (view.size() != 1) {
            throw new RuntimeException("Map size changed after rejected replace with null key");
        }
    }

    private static void expectUnsupported(Runnable action) {
        boolean thrown = false;
        try {
            action.run();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        }
    }
}
