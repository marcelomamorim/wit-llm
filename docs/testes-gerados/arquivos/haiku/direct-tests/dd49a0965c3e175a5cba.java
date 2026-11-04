/*
 * @test
 * @summary ImmutableCollections list iterator set() must throw UnsupportedOperationException and leave the list unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.ListIterator;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        List<String> list = List.of("a", "b");

        ListIterator<String> it = list.listIterator();
        it.next();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("set() after next() did not throw UnsupportedOperationException");
        }
        if (!"a".equals(list.get(0)) || !"b".equals(list.get(1)) || list.size() != 2) {
            throw new RuntimeException("immutable list was modified by set()");
        }

        ListIterator<String> it2 = list.listIterator(0);
        thrown = false;
        try {
            it2.set("y");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("set() before next() did not throw UnsupportedOperationException");
        }
    }
}
