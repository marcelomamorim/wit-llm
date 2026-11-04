/*
 * @test
 * @summary Test java.lang.System.clearProperty behavior
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) throws Exception {
        testClearPropertyReturnsPreviousValue();
        testClearPropertyReturnsNullForMissingKey();
        testClearPropertyWithNullKeyThrowsNPE();
    }

    private static void testClearPropertyReturnsPreviousValue() {
        String key = "test.clearProperty.key";
        String value = "value";
        System.setProperty(key, value);
        String removed = System.clearProperty(key);
        if (!value.equals(removed)) {
            throw new AssertionError("Expected removed value to be '" + value + "' but was '" + removed + "'");
        }
        // After removal, property should be null
        String afterRemoval = System.getProperty(key);
        if (afterRemoval != null) {
            throw new AssertionError("Expected property to be null after clearProperty but was '" + afterRemoval + "'");
        }
    }

    private static void testClearPropertyReturnsNullForMissingKey() {
        String key = "test.clearProperty.missingKey";
        // Ensure key is not set
        System.clearProperty(key);
        String removed = System.clearProperty(key);
        if (removed != null) {
            throw new AssertionError("Expected null when clearing non-existent property but got '" + removed + "'");
        }
    }

    private static void testClearPropertyWithNullKeyThrowsNPE() {
        try {
            System.clearProperty(null);
            throw new AssertionError("Expected NullPointerException when key is null");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
