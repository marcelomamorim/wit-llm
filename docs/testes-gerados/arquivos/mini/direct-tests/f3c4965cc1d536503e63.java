/*
 * @test
 * @summary Test that retainAll method in ImmutableCollections.AbstractImmutableCollection throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.Collections;
import java.util.AbstractCollection;
import java.util.function.Predicate;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollection<E> extends ImmutableCollections.AbstractImmutableCollection<E> {
        @Override
        public int size() { return 0; }
        @Override
        public java.util.Iterator<E> iterator() { return Collections.emptyIterator(); }
    }

    public static void main(String[] args) {
        TestImmutableCollection<String> coll = new TestImmutableCollection<>();

        // Test retainAll throws UnsupportedOperationException
        try {
            coll.retainAll(Collections.singleton("test"));
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        // Also test other mutating methods throw UOE
        try {
            coll.add("test");
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.addAll(Collections.singleton("test"));
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.clear();
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.remove("test");
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.removeAll(Collections.singleton("test"));
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.removeIf(new Predicate<String>() {
                public boolean test(String s) { return true; }
            });
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }

    }
}
