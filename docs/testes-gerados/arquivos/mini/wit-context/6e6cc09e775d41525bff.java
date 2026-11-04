/*
 * @test
 * @summary Test ImmutableCollections.add(E) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // The add method always throws UnsupportedOperationException
        ImmutableCollections.AbstractImmutableCollection<String> coll = new ImmutableCollections.AbstractImmutableCollection<String>() {
            public java.util.Iterator<String> iterator() {
                return java.util.Collections.emptyIterator();
            }
            public int size() {
                return 0;
            }
        };

        boolean threw = false;
        try {
            coll.add("test");
        } catch (UnsupportedOperationException uoe) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException from add method");
        }
    }
}
