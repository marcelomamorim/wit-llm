/*
 * @test
 * @summary Verify that add on immutable List and Set instances from List.of and Set.of throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        checkAddThrows(List.of(1, 2, 3), 4);
        checkAddThrows(List.of("a"), "b");
        checkAddThrows(List.of("a"), null);
        checkAddThrows(List.<Integer>of(), 1);
        checkAddThrows(Set.of("x", "y"), "z");
        checkAddThrows(Set.of("x"), null);

        List<Integer> list = List.of(1, 2);
        checkAddThrows(list, 3);
        if (list.size() != 2 || !list.get(0).equals(1) || !list.get(1).equals(2)) {
            throw new RuntimeException("immutable list changed after failed add");
        }
    }

    static <E> void checkAddThrows(Collection<E> c, E e) {
        try {
            c.add(e);
        } catch (UnsupportedOperationException ex) {
            return;
        }
        throw new RuntimeException("add did not throw UnsupportedOperationException: " + c.getClass());
    }
}
