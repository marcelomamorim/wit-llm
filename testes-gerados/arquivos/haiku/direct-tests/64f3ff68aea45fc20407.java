/*
 * @test
 * @summary Verify that removeIf on unmodifiable collection views throws UnsupportedOperationException and leaves the backing collection unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionsWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static void expectUOE(Runnable r, String msg) {
        try {
            r.run();
            failures++;
            System.err.println("FAIL (no exception): " + msg);
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        // unmodifiableCollection: removeIf must throw and not modify the backing collection
        Collection<String> base = new ArrayList<>(Arrays.asList("a", "bb", "ccc"));
        Collection<String> uc = Collections.unmodifiableCollection(base);
        expectUOE(() -> uc.removeIf(s -> s.length() > 1), "unmodifiableCollection.removeIf");
        check(base.size() == 3, "backing collection size changed after removeIf");
        check(base.contains("bb") && base.contains("ccc"), "backing collection elements changed");

        // Predicate that matches nothing still throws
        expectUOE(() -> uc.removeIf(s -> false), "unmodifiableCollection.removeIf(false predicate)");
        check(base.size() == 3, "size changed after no-match removeIf");

        // unmodifiableList: removeIf must throw
        List<Integer> baseList = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        List<Integer> ul = Collections.unmodifiableList(baseList);
        expectUOE(() -> ul.removeIf(i -> i % 2 == 0), "unmodifiableList.removeIf");
        check(baseList.equals(Arrays.asList(1, 2, 3, 4)), "backing list changed after removeIf");

        // unmodifiableSet: removeIf must throw
        Set<String> baseSet = new HashSet<>(Arrays.asList("x", "y"));
        Set<String> us = Collections.unmodifiableSet(baseSet);
        expectUOE(() -> us.removeIf(s -> true), "unmodifiableSet.removeIf");
        check(baseSet.size() == 2, "backing set changed after removeIf");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
