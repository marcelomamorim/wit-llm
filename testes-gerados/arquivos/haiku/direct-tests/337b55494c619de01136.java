/*
 * @test
 * @summary Verifies forEach on the unmodifiable collection view returned by Collections.unmodifiableCollection delegates to the backing collection
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        backing.add("c");
        Collection<String> view = Collections.unmodifiableCollection(backing);

        List<String> seen = new ArrayList<>();
        view.forEach(x -> seen.add(x));
        check(seen.equals(backing), "forEach should visit all elements in iteration order: " + seen);

        final int[] count = {0};
        Collections.unmodifiableCollection(new ArrayList<String>()).forEach(x -> count[0]++);
        check(count[0] == 0, "forEach on empty view must not invoke action");

        backing.add("d");
        List<String> seenAfter = new ArrayList<>();
        view.forEach(x -> seenAfter.add(x));
        check(seenAfter.equals(List.of("a", "b", "c", "d")) || seenAfter.equals(backing),
              "forEach should reflect backing collection changes: " + seenAfter);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
