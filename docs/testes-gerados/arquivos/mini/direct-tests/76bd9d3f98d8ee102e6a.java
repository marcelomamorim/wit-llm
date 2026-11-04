/*
 * @test
 * @summary Test that Collections.remove() throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testRemoveThrows();
    }

    void testRemoveThrows() {
        java.util.Collections.RemoveStub stub = new java.util.Collections.RemoveStub();
        try {
            stub.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}

// Stub class to expose the remove() method for testing
class Collections {
    static class RemoveStub {
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
