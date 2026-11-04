/*
 * @test
 * @summary Verifies Collections.emptyList().removeIf rejects null filters and returns false for non-null filters
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> empty = Collections.emptyList();

        Predicate<String> always = s -> true;
        boolean removed = empty.removeIf(always);
        if (removed) {
            throw new RuntimeException("removeIf on empty list must return false");
        }
        if (!empty.isEmpty()) {
            throw new RuntimeException("empty list must remain empty");
        }

        Predicate<String> nullFilter = null;
        try {
            empty.removeIf(nullFilter);
            throw new RuntimeException("expected NullPointerException for null filter");
        } catch (NullPointerException expected) {
        }
    }
}
