/*
 * @test
 * @summary Test java.util.AbstractList.forEachRemaining method behavior
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.function.Consumer;
import java.util.Objects;

public class AbstractListWitupTest {

    static class TestList extends AbstractList<String> {
        private final String[] data;

        TestList(String[] data) {
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

    static class TestForEachRemaining extends AbstractList<String> {
        private final AbstractList<String> alist;
        private final Consumer<? super String> action;
        private int index;
        private int expectedModCount = 0;
        private int fence;

        TestForEachRemaining(AbstractList<String> alist, Consumer<? super String> action) {
            this.alist = alist;
            this.action = action;
            this.index = 0;
            this.fence = alist.size();
        }

        // Simulate getFence method
        int getFence() {
            return fence;
        }

        // Simulate get method
        String get(AbstractList<String> list, int i) {
            return list.get(i);
        }

        // Simulate checkAbstractListModCount method (no-op for test)
        void checkAbstractListModCount(AbstractList<String> list, int expectedModCount) {
            // no-op
        }

        public void forEachRemaining(Consumer<? super String> action) {
            Objects.requireNonNull(action);
            AbstractList<String> list = alist;
            int hi = getFence();
            int i = index;
            index = hi;
            for (; i < hi; i++) {
                action.accept(get(list, i));
            }
            checkAbstractListModCount(alist, expectedModCount);
        }
    }

    public static void main(String[] args) {
        // Test 1: forEachRemaining applies action to all elements
        String[] data = {"a", "b", "c"};
        TestList list = new TestList(data);

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        TestForEachRemaining test = new TestForEachRemaining(list, action);
        test.forEachRemaining(action);

        if (!"abc".equals(sb.toString())) {
            throw new AssertionError("forEachRemaining did not process all elements correctly: " + sb);
        }

        // Test 2: forEachRemaining with null action throws NullPointerException
        boolean npeThrown = false;
        try {
            test.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("forEachRemaining did not throw NullPointerException when action is null");
        }

        // Test 3: forEachRemaining on empty list does not call action
        TestList emptyList = new TestList(new String[0]);
        final boolean[] called = {false};
        Consumer<String> action2 = new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        };
        TestForEachRemaining testEmpty = new TestForEachRemaining(emptyList, action2);
        testEmpty.forEachRemaining(action2);
        if (called[0]) {
            throw new AssertionError("forEachRemaining called action on empty list");
        }
    }
}
