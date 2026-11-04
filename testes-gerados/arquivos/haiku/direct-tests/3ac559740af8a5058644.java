/*
 * @test
 * @summary AbstractList.add(int, E) must throw UnsupportedOperationException by default
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;

public class AbstractListWitupTest {

    static class FixedList extends AbstractList<String> {
        @Override
        public String get(int index) {
            return "a";
        }

        @Override
        public int size() {
            return 1;
        }
    }

    public static void main(String[] args) {
        FixedList list = new FixedList();

        boolean thrown = false;
        try {
            list.add(0, "x");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("add(0, E) should throw UnsupportedOperationException");
        }

        thrown = false;
        try {
            list.add(5, "y");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("add(5, E) should throw UnsupportedOperationException");
        }

        thrown = false;
        try {
            list.add("z");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("add(E) should propagate UnsupportedOperationException from add(int, E)");
        }

        if (list.size() != 1) {
            throw new AssertionError("size must remain unchanged after failed add, was " + list.size());
        }
        if (!"a".equals(list.get(0))) {
            throw new AssertionError("element must remain unchanged after failed add");
        }
    }
}
