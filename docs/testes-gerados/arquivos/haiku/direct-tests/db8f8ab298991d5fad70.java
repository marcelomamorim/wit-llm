/*
 * @test
 * @summary removeIf on immutable collections from List.of and Set.of must throw UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class ImmutableCollectionsWitupTest {

    interface Action {
        void run();
    }

    static void expectUoe(String name, Action action) {
        try {
            action.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException: " + name);
    }

    public static void main(String[] args) {
        Predicate<Object> all = o -> true;
        Predicate<Object> none = o -> false;

        expectUoe("List.of(1,2,3).removeIf(all)", () -> List.of(1, 2, 3).removeIf(all));
        expectUoe("List.of(1,2,3).removeIf(none)", () -> List.of(1, 2, 3).removeIf(none));
        expectUoe("List.of().removeIf(none)", () -> List.of().removeIf(none));
        expectUoe("List.of(1).removeIf(null)", () -> List.of(1).removeIf(null));
        expectUoe("Set.of(\"a\").removeIf(all)", () -> Set.of("a").removeIf(all));

        List<Integer> list = List.of(1, 2, 3);
        if (list.size() != 3 || !list.contains(2)) {
            throw new RuntimeException("List contents changed after removeIf attempts");
        }
    }
}
