/*
 * @test
 * @summary Test that removeIf method in ImmutableCollections.AbstractImmutableCollection throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.Predicate;
import java.util.AbstractCollection;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        AbstractImmutableCollection<String> coll = new AbstractImmutableCollection<String>() {
            public java.util.Iterator<String> iterator() {
                return java.util.Collections.emptyIterator();
            }
            public int size() {
                return 0;
            }
        };

        boolean thrown = false;
        try {
            coll.removeIf(new Predicate<String>() {
                public boolean test(String s) {
                    return true;
                }
            });
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from removeIf");
        }
    }
}
