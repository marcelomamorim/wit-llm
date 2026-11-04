/*
 * @test
 * @summary Test Collections.remove(Object key, Object value) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testRemoveThrows();
        System.out.println("Test passed.");
    }

    public void testRemoveThrows() {
        try {
            // The method under test always throws UnsupportedOperationException
            java.util.Collections.remove(new Object(), new Object());
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
