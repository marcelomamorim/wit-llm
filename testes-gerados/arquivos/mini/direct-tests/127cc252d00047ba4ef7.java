/*
 * @test
 * @summary Test that ImmutableCollections.AbstractImmutableList.add(int, E) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;
import java.util.List;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an instance of AbstractImmutableList via an anonymous subclass
        ImmutableCollections.AbstractImmutableList<String> list = new ImmutableCollections.AbstractImmutableList<String>() {
            @Override
            public String get(int index) {
                throw new IndexOutOfBoundsException();
            }
            @Override
            public int size() {
                return 0;
            }
        };

        // Test that add(int, E) throws UnsupportedOperationException
        try {
            list.add(0, "test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
