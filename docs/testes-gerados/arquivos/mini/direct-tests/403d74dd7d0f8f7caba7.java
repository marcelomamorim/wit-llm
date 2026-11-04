/*
 * @test
 * @summary Test that ImmutableCollections remove(int) method throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.AbstractList;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of AbstractImmutableList to test remove(int)
        AbstractList<String> list = new ImmutableCollections.AbstractImmutableList<String>() {
            @Override
            public String get(int index) {
                return "test";
            }
            @Override
            public int size() {
                return 1;
            }
        };

        try {
            list.remove(0);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
