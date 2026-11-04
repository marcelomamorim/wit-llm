/*
 * @test
 * @summary Verify that addAll(int, Collection) on immutable lists always throws UnsupportedOperationException and leaves the list unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        List<Integer> three = List.of(1, 2, 3);
        expectUOE(three, 0, Arrays.asList(4, 5));
        expectUOE(three, 3, Arrays.asList(4, 5));
        expectUOE(three, 1, Collections.emptyList());
        if (three.size() != 3 || !three.equals(List.of(1, 2, 3))) {
            throw new RuntimeException("immutable list was modified: " + three);
        }

        List<Integer> empty = List.of();
        expectUOE(empty, 0, Collections.emptyList());
        if (!empty.isEmpty()) {
            throw new RuntimeException("empty immutable list was modified: " + empty);
        }
    }

    static void expectUOE(List<Integer> list, int index, List<Integer> c) {
        try {
            list.addAll(index, c);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for addAll(" + index + ", ...)");
    }
}
