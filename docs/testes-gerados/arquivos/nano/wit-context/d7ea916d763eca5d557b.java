/*
 * @test
 * @summary Test for ConcurrentSkipListMap.remove() method, focusing on exception behavior.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsIllegalStateExceptionWhenLastReturnedIsNull();
    }

    private static void testRemoveThrowsIllegalStateExceptionWhenLastReturnedIsNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        // No elements added, lastReturned is null by default
        try {
            // Create an instance of the inner class via reflection or assume accessible
            // Since remove() is public, we can instantiate a subclass for testing
            class TestSubMapIter extends ConcurrentSkipListMap.SubMap.SubMapIter {
                public TestSubMapIter() {
                    super();
                }
                @Override
                public void remove() {
                    super.remove();
                }
            }
            TestSubMapIter iterator = new TestSubMapIter();
            // lastReturned is null, calling remove() should throw IllegalStateException
            iterator.remove();
            // If no exception, test fails
            System.err.println("Expected IllegalStateException was not thrown.");
            System.exit(1);
        } catch (IllegalStateException e) {
            // Expected exception
            System.out.println("Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
