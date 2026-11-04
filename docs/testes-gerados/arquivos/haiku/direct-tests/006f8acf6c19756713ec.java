/*
 * @test
 * @summary Verifies that forEachRemaining on the iterator returned by Collections.unmodifiableCollection visits only the remaining elements
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        List<String> src = Arrays.asList("a", "b", "c", "d");

        Iterator<String> it = Collections.unmodifiableCollection(src).iterator();
        check(it.next().equals("a"));
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("b", "c", "d")));
        check(!it.hasNext());

        List<String> all = new ArrayList<>();
        Collections.unmodifiableCollection(src).iterator().forEachRemaining(all::add);
        check(all.equals(Arrays.asList("a", "b", "c", "d")));

        List<String> empty = new ArrayList<>();
        Collections.unmodifiableCollection(new ArrayList<String>())
                .iterator().forEachRemaining(empty::add);
        check(empty.isEmpty());

        Iterator<String> exhausted = Collections.unmodifiableCollection(src).iterator();
        while (exhausted.hasNext()) {
            exhausted.next();
        }
        List<String> none = new ArrayList<>();
        exhausted.forEachRemaining(none::add);
        check(none.isEmpty());
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Assertion failed");
        }
    }
}
