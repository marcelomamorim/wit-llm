/*
 * @test
 * @summary Verify that AbstractList.set throws UnsupportedOperationException by default and leaves the list unchanged
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.List;

public class AbstractListWitupTest {

    static class FixedList extends AbstractList<String> {
        private final String[] data;

        FixedList(String... data) {
            this.data = data;
        }

        @Override
        public String get(int index) {
            return data[index];
        }

        @Override
        public int size() {
            return data.length;
        }
    }

    static void expectUnsupported(List<String> list, int index, String element) {
        try {
            list.set(index, element);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for set("
                + index + ", " + element + ")");
    }

    static void checkUnchanged(FixedList list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) {
                throw new RuntimeException("element " + i + " unexpectedly null");
            }
        }
    }

    public static void main(String[] args) {
        FixedList list = new FixedList("a", "b", "c");

        expectUnsupported(list, 0, "x");
        expectUnsupported(list, 2, null);
        expectUnsupported(list, 5, "y");

        if (!"a".equals(list.get(0)) || !"b".equals(list.get(1)) || !"c".equals(list.get(2))) {
            throw new RuntimeException("list was modified by failed set");
        }
        if (list.size() != 3) {
            throw new RuntimeException("unexpected size: " + list.size());
        }

        FixedList empty = new FixedList();
        expectUnsupported(empty, 0, "x");
        if (empty.size() != 0) {
            throw new RuntimeException("empty list changed size");
        }
    }
}
