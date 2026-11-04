/*
 * @test
 * @summary Test Collections$UnmodifiableCollection add method throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create an unmodifiable collection wrapper to test add(E e) method
        java.util.List<String> list = java.util.Collections.unmodifiableList(new java.util.ArrayList<>());
        try {
            list.add("test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
