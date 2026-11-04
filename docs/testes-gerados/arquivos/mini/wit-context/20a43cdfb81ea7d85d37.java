/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableList.indexOf(Object) for null input and normal cases
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Objects;
import java.util.List;
import java.util.AbstractList;

public class ImmutableCollectionsWitupTest {

    // Minimal AbstractImmutableList subclass to test indexOf
    static class TestImmutableList extends ImmutableCollections.AbstractImmutableList<String> {
        private final String[] elements;

        TestImmutableList(String[] elements) {
            this.elements = elements.clone();
        }

        @Override
        public String get(int index) {
            return elements[index];
        }

        @Override
        public int size() {
            return elements.length;
        }
    }

    public static void main(String[] args) {
        testIndexOfNullThrows();
        testIndexOfFound();
        testIndexOfNotFound();
        testIndexOfEmpty();
    }

    private static void testIndexOfNullThrows() {
        TestImmutableList list = new TestImmutableList(new String[] {"a", "b", "c"});
        try {
            list.indexOf(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testIndexOfFound() {
        TestImmutableList list = new TestImmutableList(new String[] {"a", "b", "c"});
        int idx = list.indexOf("b");
        if (idx != 1) {
            throw new RuntimeException("indexOf returned " + idx + ", expected 1");
        }
    }

    private static void testIndexOfNotFound() {
        TestImmutableList list = new TestImmutableList(new String[] {"a", "b", "c"});
        int idx = list.indexOf("x");
        if (idx != -1) {
            throw new RuntimeException("indexOf returned " + idx + ", expected -1 for not found");
        }
    }

    private static void testIndexOfEmpty() {
        TestImmutableList list = new TestImmutableList(new String[0]);
        int idx = list.indexOf("any");
        if (idx != -1) {
            throw new RuntimeException("indexOf returned " + idx + ", expected -1 for empty list");
        }
    }
}
