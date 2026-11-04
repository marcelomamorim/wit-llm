/*
 * @test
 * @summary Verify that remove(Object) on immutable collections from List.of and Set.of throws UnsupportedOperationException and leaves the collection unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        expectUOE(List.of(1, 2), 1);
        expectUOE(List.of(1, 2, 3), 2);
        expectUOE(List.of(1, 2, 3), 99);
        expectUOE(List.of(), null);
        expectUOE(Set.of("a", "b"), "a");
        expectUOE(Set.of("a", "b", "c"), "z");
    }

    static void expectUOE(Collection<?> c, Object o) {
        int before = c.size();
        try {
            c.remove(o);
        } catch (UnsupportedOperationException e) {
            if (c.size() != before) {
                throw new RuntimeException("size changed after failed remove");
            }
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for remove(" + o + ")");
    }
}
