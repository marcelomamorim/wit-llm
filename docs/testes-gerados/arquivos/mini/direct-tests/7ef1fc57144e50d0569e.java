/*
 * @test
 * @summary Test that ImmutableCollections.AbstractImmutableList.set throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.AbstractList;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        AbstractList<String> list = new ImmutableCollections.AbstractImmutableList<String>() {
            @Override
            public String get(int index) {
                return "test";
            }
            @Override
            public int size() {
                return 1;
            }
            @Override
            public String set(int index, String element) {
                return super.set(index, element);
            }
        };

        try {
            list.set(0, "new");
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
