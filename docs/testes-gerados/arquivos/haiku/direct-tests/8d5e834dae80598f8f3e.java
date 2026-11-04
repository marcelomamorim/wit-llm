/*
 * @test
 * @summary Verifies that AbstractList.remove(int) throws UnsupportedOperationException and leaves the list unchanged
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;

public class AbstractListWitupTest {

    static class Two extends AbstractList<String> {
        @Override
        public String get(int index) {
            return "v" + index;
        }

        @Override
        public int size() {
            return 2;
        }
    }

    static class Empty extends AbstractList<String> {
        @Override
        public String get(int index) {
            throw new IndexOutOfBoundsException("index: " + index);
        }

        @Override
        public int size() {
            return 0;
        }
    }

    static void expectUnsupported(AbstractList<String> list, int index) {
        try {
            list.remove(index);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("UnsupportedOperationException not thrown for index " + index);
    }

    public static void main(String[] args) {
        Two two = new Two();
        expectUnsupported(two, 0);
        expectUnsupported(two, 1);
        expectUnsupported(two, -1);

        if (two.size() != 2) {
            throw new RuntimeException("size changed after failed remove: " + two.size());
        }
        if (!"v0".equals(two.get(0)) || !"v1".equals(two.get(1))) {
            throw new RuntimeException("elements changed after failed remove");
        }

        Empty empty = new Empty();
        expectUnsupported(empty, 0);
        if (empty.size() != 0) {
            throw new RuntimeException("empty list size changed");
        }
    }
}
