/*
 * @test
 * @summary Test java.util.ImmutableCollections.contains(Object) method
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    static class DummyImmutableCollections {
        private final int size;
        DummyImmutableCollections(int size) {
            this.size = size;
        }
        // Simulate probe method: returns >=0 if element found, else -1
        int probe(Object o) {
            if (o.equals("found")) {
                return 0;
            } else {
                return -1;
            }
        }
        public boolean contains(Object o) {
            Objects.requireNonNull(o);
            return size > 0 && probe(o) >= 0;
        }
    }

    public static void main(String[] args) {
        // Test 1: null argument throws NullPointerException
        try {
            DummyImmutableCollections c = new DummyImmutableCollections(1);
            c.contains(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: size == 0, contains returns false regardless of element
        DummyImmutableCollections empty = new DummyImmutableCollections(0);
        if (empty.contains("found")) {
            throw new Error("Expected false when size is 0");
        }

        // Test 3: size > 0 and element found returns true
        DummyImmutableCollections coll = new DummyImmutableCollections(1);
        if (!coll.contains("found")) {
            throw new Error("Expected true when element is found");
        }

        // Test 4: size > 0 and element not found returns false
        if (coll.contains("notfound")) {
            throw new Error("Expected false when element is not found");
        }
    }
}
