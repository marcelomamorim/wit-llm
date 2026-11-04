/*
 * @test
 * @summary Verifies that AbstractList.remove(int) unconditionally throws UnsupportedOperationException and leaves the list unchanged
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
        checkRemoveThrows(new FixedList("a", "b"), 0);
        checkRemoveThrows(new FixedList("a", "b"), 1);
        checkRemoveThrows(new FixedList(), 0);
        checkRemoveThrows(new FixedList("x"), -1);
    }

    static void checkRemoveThrows(List<String> list, int index) {
        int sizeBefore = list.size();
        try {
            list.remove(index);
        } catch (UnsupportedOperationException e) {
            if (list.size() != sizeBefore) {
                throw new RuntimeException("List size changed after failed remove(" + index + ")");
            }
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException for remove(" + index + ")");
    }
}
