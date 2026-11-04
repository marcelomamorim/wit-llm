/*
 * @test
 * @summary Test that Collections.setValue throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSetValueThrows();
    }

    static void testSetValueThrows() {
        java.util.Map.Entry<String, String> entry = new java.util.AbstractMap.SimpleEntry<String, String>("key", "value") {
            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            entry.setValue("newValue");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
