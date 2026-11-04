/*
 * @test
 * @summary Verify that retainAll on immutable collections from List.of and Set.of always throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    interface Action {
        void run();
    }

    static void expectUoe(Action action, String description) {
        try {
            action.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException: " + description);
    }

    public static void main(String[] args) {
        List<Integer> list1 = List.of(1);
        List<Integer> list3 = List.of(1, 2, 3);
        List<Integer> listEmpty = List.of();
        Set<String> set1 = Set.of("a");
        Set<String> set3 = Set.of("a", "b", "c");
        Collection<Integer> keep = Arrays.asList(1, 2);

        expectUoe(() -> list1.retainAll(keep), "List.of(1).retainAll(nonEmpty)");
        expectUoe(() -> list3.retainAll(keep), "List.of(1,2,3).retainAll(nonEmpty)");
        expectUoe(() -> listEmpty.retainAll(keep), "List.of().retainAll(nonEmpty)");
        expectUoe(() -> list3.retainAll(new ArrayList<Integer>()), "List.of(1,2,3).retainAll(empty)");
        expectUoe(() -> list1.retainAll(null), "List.of(1).retainAll(null)");
        expectUoe(() -> set1.retainAll(Arrays.asList("a")), "Set.of(\"a\").retainAll(nonEmpty)");
        expectUoe(() -> set3.retainAll(Arrays.asList("a", "b")), "Set.of(a,b,c).retainAll(nonEmpty)");
        expectUoe(() -> set3.retainAll(null), "Set.of(a,b,c).retainAll(null)");

        if (list3.size() != 3 || !list3.contains(1) || !list3.contains(2) || !list3.contains(3)) {
            throw new RuntimeException("List.of contents changed after retainAll");
        }
        if (set3.size() != 3 || !set3.contains("a") || !set3.contains("b") || !set3.contains("c")) {
            throw new RuntimeException("Set.of contents changed after retainAll");
        }
    }
}
