/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableList.replaceAll throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.UnaryOperator;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of AbstractImmutableList to test replaceAll
        ImmutableCollections.AbstractImmutableList<String> list = new ImmutableCollections.AbstractImmutableList<String>() {
            @Override
            public int size() {
                return 0;
            }
            @Override
            public String get(int index) {
                throw new IndexOutOfBoundsException();
            }
        };

        UnaryOperator<String> operator = new UnaryOperator<String>() {
            public String apply(String s) {
                return s.toUpperCase();
            }
        };

        try {
            list.replaceAll(operator);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
