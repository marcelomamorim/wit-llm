/* @test    @summary Test for AbstractList.forEachRemaining method    @run main AbstractListWitupTest */

import java.util.AbstractList;
import java.util.function.Consumer;

public class AbstractListWitupTest {

    static class DummyList extends AbstractList<Integer> {
        private final Integer[] data;
        private int modCount = 0;

        DummyList(Integer[] data) {
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

        @Override
        public Integer set(int index, Integer element) {
            Integer old = data[index];
            data[index] = element;
            modCount++;
            return old;
        }

        @Override
        public void add(int index, Integer element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Integer remove(int index) {
            throw new UnsupportedOperationException();
        }

        int getFence() {
            return size();
        }

        int getModCount() {
            return modCount;
        }
    }

    public static void main(String[] args) {
        Integer[] data = {1, 2, 3, 4, 5};
        DummyList list = new DummyList(data);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e).append(",");
        list.forEachRemaining(consumer);
        String result = sb.toString();
        if (!"1,2,3,4,5,".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
        if (list.getModCount() != 0) {
            throw new AssertionError("ModCount should not change in forEachRemaining");
        }
        System.out.println("Test passed.");
    }
}
