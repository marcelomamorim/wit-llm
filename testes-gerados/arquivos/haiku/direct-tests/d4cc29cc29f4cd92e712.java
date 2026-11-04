/*
 * @test
 * @summary Verifies that iterators over immutable List and Set instances reject remove() with UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testListIteratorRemoveThrows();
        testSetIteratorRemoveThrows();
    }

    static void testListIteratorRemoveThrows() {
        List<String> list = List.of("a", "b");
        Iterator<String> it = list.iterator();
        it.next();
        expectUoe(it, "List.of iterator");
        if (list.size() != 2 || !list.get(0).equals("a") || !list.get(1).equals("b")) {
            throw new RuntimeException("List.of contents changed after remove() attempt");
        }
    }

    static void testSetIteratorRemoveThrows() {
        Set<Integer> set = Set.of(1);
        Iterator<Integer> it = set.iterator();
        it.next();
        expectUoe(it, "Set.of iterator");
        if (set.size() != 1 || !set.contains(1)) {
            throw new RuntimeException("Set.of contents changed after remove() attempt");
        }
    }

    static void expectUoe(Iterator<?> it, String name) {
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException(name + ": remove() did not throw UnsupportedOperationException");
    }
}
