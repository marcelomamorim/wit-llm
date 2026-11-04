/*
 * @test
 * @summary Verifies Collections.emptyList().removeIf returns false and rejects null filters
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> empty = Collections.emptyList();

        Predicate<String> alwaysTrue = s -> true;
        if (empty.removeIf(alwaysTrue)) {
            throw new RuntimeException("removeIf(alwaysTrue) on empty list returned true");
        }

        Predicate<String> alwaysFalse = s -> false;
        if (empty.removeIf(alwaysFalse)) {
            throw new RuntimeException("removeIf(alwaysFalse) on empty list returned true");
        }

        boolean npeThrown = false;
        try {
            empty.removeIf(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("removeIf(null) did not throw NullPointerException");
        }

        if (!empty.isEmpty()) {
            throw new RuntimeException("empty list changed after removeIf calls");
        }
    }
}
