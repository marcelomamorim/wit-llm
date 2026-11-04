/*
 * @test
 * @summary Verify that remove(int) on immutable lists from List.of throws UnsupportedOperationException and leaves the list unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        List<Integer> empty = List.of();
        expectUoe(empty, 0);

        List<Integer> one = List.of(1);
        expectUoe(one, 0);
        if (one.get(0) != 1) {
            throw new RuntimeException("list with one element was modified");
        }

        List<Integer> three = List.of(1, 2, 3);
        expectUoe(three, 0);
        expectUoe(three, 2);
        if (three.size() != 3 || three.get(0) != 1 || three.get(1) != 2 || three.get(2) != 3) {
            throw new RuntimeException("list with three elements was modified");
        }

        List<Integer> many = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        expectUoe(many, 5);
        if (many.size() != 11) {
            throw new RuntimeException("large list size changed");
        }
    }

    private static void expectUoe(List<Integer> list, int index) {
        int before = list.size();
        try {
            list.remove(index);
        } catch (UnsupportedOperationException e) {
            if (list.size() != before) {
                throw new RuntimeException("size changed after failed remove");
            }
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for index " + index);
    }
}
