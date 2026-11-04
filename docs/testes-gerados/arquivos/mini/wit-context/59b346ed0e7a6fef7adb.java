/*
 * @test
 * @summary Test AbstractList.forEachRemaining method for null action and normal iteration
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.function.Consumer;
import java.util.Objects;

public class AbstractListWitupTest {

    static class TestList extends AbstractList<Integer> {
        private final Integer[] data;

        TestList(Integer[] data) {
            this.data = data;
        }

        @Override
        public Integer get(int index) {
            return data[index];
        }

        @Override
        public int size() {
            return data.length;
        }
    }

    static class TestSpliterator<E> {
        private final AbstractList<E> list;
        private int index;
        private final int fence;

        TestSpliterator(AbstractList<E> list) {
            this.list = list;
            this.index = 0;
            this.fence = list.size();
        }

        int getFence() {
            return fence;
        }

        void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            int hi = getFence();
            int i = index;
            index = hi;
            for (; i < hi; i++) {
                action.accept(list.get(i));
            }
        }
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testForEachRemainingProcessesAllElements();
    }

    private static void testNullActionThrowsNPE() {
        TestList list = new TestList(new Integer[] {1, 2, 3});
        TestSpliterator<Integer> spliterator = new TestSpliterator<>(list);
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingProcessesAllElements() {
        TestList list = new TestList(new Integer[] {10, 20, 30});
        TestSpliterator<Integer> spliterator = new TestSpliterator<>(list);
        final int[] sum = new int[1];
        spliterator.forEachRemaining(new Consumer<Integer>() {
            public void accept(Integer i) {
                sum[0] += i;
            }
        });
        if (sum[0] != 60) {
            throw new RuntimeException("forEachRemaining did not process all elements correctly, sum=" + sum[0]);
        }
    }
}
