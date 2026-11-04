/*
 * @test
 * @summary Test Collections.EmptyIterator.remove() throws IllegalStateException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        java.util.Iterator<?> it = java.util.Collections.emptyIterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
