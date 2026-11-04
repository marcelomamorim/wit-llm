/*
 * @test
 * @summary Verifies forEachRemaining on iterators returned by Collections.unmodifiableList
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> unmod = Collections.unmodifiableList(base);

        Iterator<String> nullIt = unmod.listIterator();
        try {
            nullIt.forEachRemaining(null);
            throw new RuntimeException("NullPointerException expected for null action");
        } catch (NullPointerException expected) {
        }

        Iterator<String> it = unmod.listIterator();
        if (!it.next().equals("a")) {
            throw new RuntimeException("unexpected first element");
        }
        List<String> collected = new ArrayList<>();
        it.forEachRemaining(collected::add);
        if (!collected.equals(Arrays.asList("b", "c"))) {
            throw new RuntimeException("unexpected remaining elements: " + collected);
        }
        if (it.hasNext()) {
            throw new RuntimeException("iterator should be exhausted");
        }
    }
}
