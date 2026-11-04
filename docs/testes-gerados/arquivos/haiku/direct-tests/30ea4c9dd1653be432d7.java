/*
 * @test
 * @summary Verifies removeIf delegation behavior of synchronized collection wrappers created by Collections
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        List<Integer> base = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Collection<Integer> sync = Collections.synchronizedCollection(base);
        Predicate<Integer> even = x -> x % 2 == 0;

        check(sync.removeIf(even), "removeIf should return true when elements are removed");
        check(base.equals(Arrays.asList(1, 3, 5)), "only non-matching elements should remain");
        check(!sync.removeIf(even), "removeIf should return false when nothing matches");
        check(base.size() == 3, "size should stay 3 after no-op removeIf");

        List<String> sl = Collections.synchronizedList(
                new ArrayList<>(Arrays.asList("a", "bb", "ccc")));
        check(sl.removeIf(s -> s.length() > 1), "synchronizedList removeIf should report removal");
        check(sl.equals(Arrays.asList("a")), "synchronizedList should keep only matching-negated elements");

        Collection<Integer> empty = Collections.synchronizedCollection(new ArrayList<Integer>());
        check(!empty.removeIf(even), "removeIf on empty collection should return false");

        Collection<Integer> allEven = Collections.synchronizedCollection(
                new ArrayList<>(Arrays.asList(2, 4)));
        check(allEven.removeIf(even) && allEven.isEmpty(),
              "removeIf matching all elements should empty the collection");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
