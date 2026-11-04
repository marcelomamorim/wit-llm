/*
 * @test
 * @summary Verifies that AbstractList.set(int, E) always throws UnsupportedOperationException for a minimal read-only subclass
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;

public class AbstractListWitupTest {
    static class Minimal extends AbstractList<String> {
        @Override
        public String get(int index) {
            return "x";
        }

        @Override
        public int size() {
            return 1;
        }
    }

    public static void main(String[] args) {
        Minimal list = new Minimal();
        expectUnsupported(list, 0, "a");
        expectUnsupported(list, -1, null);
        expectUnsupported(list, 5, "b");
        if (list.size() != 1 || !"x".equals(list.get(0))) {
            throw new RuntimeException("list state changed after set");
        }
    }

    static void expectUnsupported(AbstractList<String> list, int index, String element) {
        try {
            list.set(index, element);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for index " + index);
    }
}
