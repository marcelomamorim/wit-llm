/*
 * @test
 * @summary WITUP-derived tests for AbstractList.subList range checks
 * @run main AbstractListWitupTest
 */

import java.util.List;

public class AbstractListWitupTest {

    static class FixedList extends AbstractList<Integer> {
        private final int n;

        FixedList(int n) {
            this.n = n;
        }

        public Integer get(int index) {
            if (index < 0 || index >= n) {
                throw new IndexOutOfBoundsException();
            }
            return index;
        }

        public int size() {
            return n;
        }
    }

    static void expectThrows(Class<? extends Throwable> expected, List<Integer> list,
                             int from, int to) {
        try {
            list.subList(from, to);
        } catch (Throwable t) {
            if (!expected.isInstance(t)) {
                throw new RuntimeException("unexpected exception " + t + " for (" + from + "," + to + ")");
            }
            return;
        }
        throw new RuntimeException("no exception for (" + from + "," + to + ")");
    }

    public static void main(String[] args) {
        FixedList list = new FixedList(3);

        List<Integer> sub = list.subList(1, 3);
        if (sub.size() != 2 || sub.get(0) != 1 || sub.get(1) != 2) {
            throw new RuntimeException("wrong sublist contents");
        }

        if (!list.subList(0, 0).isEmpty()) {
            throw new RuntimeException("empty range should be empty");
        }

        if (list.subList(3, 3).size() != 0) {
            throw new RuntimeException("boundary range at size should be empty");
        }

        if (new FixedList(0).subList(0, 0).size() != 0) {
            throw new RuntimeException("empty list empty range should be empty");
        }

        expectThrows(IllegalArgumentException.class, list, 2, 1);
        expectThrows(IndexOutOfBoundsException.class, list, 0, 4);
        expectThrows(IndexOutOfBoundsException.class, list, -1, 2);
        expectThrows(IndexOutOfBoundsException.class, list, 4, 4);
    }
}
