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
        java.util.Collections.EmptyMap<String, String> emptyMap = new java.util.Collections.EmptyMap<>();
        boolean thrown = false;
        try {
            emptyMap.replace("key", "oldValue", "newValue");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by replace method");
        }
    }
}
