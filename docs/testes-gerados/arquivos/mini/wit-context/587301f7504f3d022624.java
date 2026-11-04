/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableList.addAll(int, Collection) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableList<E> extends ImmutableCollections.AbstractImmutableList<E> {
        private final List<E> list;
        TestImmutableList(List<E> list) {
            this.list = list;
        }
        @Override
        public E get(int index) {
            return list.get(index);
        }
        @Override
        public int size() {
            return list.size();
        }
    }

    public static void main(String[] args) {
        // Prepare a sample immutable list instance
        List<String> baseList = new ArrayList<>();
        baseList.add("a");
        baseList.add("b");
        baseList.add("c");
        TestImmutableList<String> immList = new TestImmutableList<>(Collections.unmodifiableList(baseList));

        // Prepare a collection to add
        Collection<String> toAdd = new ArrayList<>();
        toAdd.add("x");
        toAdd.add("y");

        // Test that addAll(int, Collection) throws UnsupportedOperationException
        try {
            immList.addAll(1, toAdd);
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Also test that other mutating methods throw UOE
        try {
            immList.add("z");
            throw new AssertionError("Expected UnsupportedOperationException not thrown on add");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            immList.remove("a");
            throw new AssertionError("Expected UnsupportedOperationException not thrown on remove");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            immList.clear();
            throw new AssertionError("Expected UnsupportedOperationException not thrown on clear");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
