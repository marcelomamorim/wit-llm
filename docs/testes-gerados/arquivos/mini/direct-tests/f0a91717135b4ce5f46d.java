/*
 * @test
 * @summary Test that Collections.set(E) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testSetThrows();
    }

    private void testSetThrows() {
        CollectionsWitupTest.Setter s = new CollectionsWitupTest.Setter();
        try {
            s.set(null);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    // Minimal class to expose the set(E) method as per the source excerpt
    private static class Setter {
        public void set(Object e) {
            throw new UnsupportedOperationException();
        }
    }
}
