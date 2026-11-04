/*
 * @test
 * @summary Test Collections.pollFirst() throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testPollFirstThrows();
    }

    private void testPollFirstThrows() {
        java.util.Collections c = new java.util.Collections() {
            @Override
            public Object pollFirst() {
                return super.pollFirst();
            }
        };

        try {
            c.pollFirst();
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
