/*
 * @test
 * @summary Test ImmutableCollections.set method throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        ImmutableCollections.AbstractImmutableList<String> list = new ImmutableCollections.AbstractImmutableList<String>() {
            @Override
            public int size() { return 0; }
            @Override
            public String get(int index) { throw new IndexOutOfBoundsException(); }
        };

        boolean threw = false;
        try {
            list.set(0, "test");
        } catch (UnsupportedOperationException uoe) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException from set method");
        }
    }
}
