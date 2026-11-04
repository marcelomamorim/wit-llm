/*
 * @test
 * @summary Test that replaceAll method in ImmutableCollections throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.UnaryOperator;
import java.util.AbstractList;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of AbstractImmutableCollection with replaceAll method
        AbstractList<String> immutableList = new AbstractList<String>() {
            @Override
            public String get(int index) {
                return "test";
            }
            @Override
            public int size() {
                return 1;
            }
            @Override
            public void replaceAll(UnaryOperator<String> operator) {
                throw ImmutableCollections.uoe();
            }
        };

        boolean thrown = false;
        try {
            immutableList.replaceAll(s -> s.toUpperCase());
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
