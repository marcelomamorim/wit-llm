/*
 * @test
 * @summary WITUP-derived check that Collections.emptyMap() rejects replace(K, V) with UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, String> empty = Collections.emptyMap();

        boolean thrown = false;
        try {
            empty.replace("key", "value");
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("replace(K, V) did not throw UnsupportedOperationException");
        }

        thrown = false;
        try {
            empty.replace(null, null);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("replace(null, null) did not throw UnsupportedOperationException");
        }

        if (!empty.isEmpty()) {
            throw new RuntimeException("emptyMap() should remain empty after rejected replace");
        }
    }
}
