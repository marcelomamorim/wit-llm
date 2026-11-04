/*
 * @test
 * @summary Verify that AbstractList.add(int, E) throws UnsupportedOperationException and leaves the list unchanged
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

    public static void main(String[] args) {
        checkAddThrows(new FixedList("a", "b"), 0, "x");
        checkAddThrows(new FixedList("a", "b"), 1, "x");
        checkAddThrows(new FixedList("a", "b"), 2, "x");
        checkAddThrows(new FixedList(), 0, "x");
        checkAddThrows(new FixedList("a"), 0, null);
    }

    static void checkAddThrows(List<String> list, int index, String element) {
        int before = list.size();
        try {
            list.add(index, element);
        } catch (UnsupportedOperationException e) {
            if (list.size() != before) {
                throw new RuntimeException("list size changed after failed add");
            }
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for index " + index);
    }
}
