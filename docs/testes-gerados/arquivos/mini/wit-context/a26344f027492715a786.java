/*
 * @test
 * @summary Test Collections.replace method throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceThrows();
        System.out.println("Test passed.");
    }

    private static void testReplaceThrows() {
        java.util.Collections.SingletonMap<String, String> singletonMap = new java.util.Collections.SingletonMap<>("key", "value");
        boolean thrown = false;
        try {
            singletonMap.replace("key", "value", "newValue");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by replace method");
        }
    }
}
