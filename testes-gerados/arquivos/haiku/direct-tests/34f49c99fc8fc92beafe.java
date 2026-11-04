/*
 * @test
 * @summary Verifies removeIf behavior through public Collections wrapper APIs
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
        Predicate<Integer> even = x -> x % 2 == 0;

        // synchronizedCollection: removes matching elements and returns true
        List<Integer> base = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Collection<Integer> sc = Collections.synchronizedCollection(base);
        boolean changed = sc.removeIf(even);
        if (!changed) {
            throw new RuntimeException("removeIf should return true when elements match");
        }
        if (!base.equals(Arrays.asList(1, 3, 5))) {
            throw new RuntimeException("Unexpected contents: " + base);
        }

        // synchronizedCollection: no match returns false and leaves contents unchanged
        List<Integer> noMatch = new ArrayList<>(Arrays.asList(1, 3, 5));
        Collection<Integer> sc2 = Collections.synchronizedCollection(noMatch);
        if (sc2.removeIf(even)) {
            throw new RuntimeException("removeIf should return false when no element matches");
        }
        if (!noMatch.equals(Arrays.asList(1, 3, 5))) {
            throw new RuntimeException("Contents changed unexpectedly: " + noMatch);
        }

        // synchronizedList: same delegation semantics
        List<Integer> sl = Collections.synchronizedList(
                new ArrayList<>(Arrays.asList(2, 4, 7)));
        if (!sl.removeIf(even)) {
            throw new RuntimeException("synchronizedList.removeIf should return true");
        }
        if (!sl.equals(Arrays.asList(7))) {
            throw new RuntimeException("Unexpected synchronizedList contents: " + sl);
        }

        // unmodifiableCollection: removeIf must reject mutation
        Collection<Integer> um = Collections.unmodifiableCollection(
                new ArrayList<>(Arrays.asList(1, 2, 3)));
        boolean thrown = false;
        try {
            um.removeIf(even);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("unmodifiableCollection.removeIf should throw UnsupportedOperationException");
        }
    }
}
