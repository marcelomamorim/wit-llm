/*
 * @test
 * @summary Test ImmutableCollections.remove(int) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        ImmutableCollections.AbstractImmutableList<String> list = new ImmutableCollections.AbstractImmutableList<String>() {
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
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
