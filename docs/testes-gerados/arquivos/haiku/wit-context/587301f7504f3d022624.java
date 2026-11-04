/*
 * @test
 * @summary Verifies that addAll(int, Collection) on immutable lists from List.of throws UnsupportedOperationException and leaves the list unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        throw new RuntimeException("Expected UnsupportedOperationException for " + name);
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3);

        expectUoe("addAll(0, non-empty)",
                () -> list.addAll(0, Arrays.asList(4, 5)));
        expectUoe("addAll(1, empty)",
                () -> list.addAll(1, Collections.<Integer>emptyList()));
        expectUoe("addAll(size, non-empty)",
                () -> list.addAll(list.size(), Arrays.asList(6)));
        expectUoe("empty list addAll(0, empty)",
                () -> List.<Integer>of().addAll(0, Collections.<Integer>emptyList()));

        if (list.size() != 3 || !list.equals(Arrays.asList(1, 2, 3))) {
            throw new RuntimeException("Immutable list was modified: " + list);
        }
    }
}
