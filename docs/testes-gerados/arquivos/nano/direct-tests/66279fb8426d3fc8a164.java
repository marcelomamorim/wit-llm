/* @test    @summary Test that PreHashedMap.remove() throws UnsupportedOperationException    @run main PreHashedMapWitupTest */

import sun.util.PreHashedMap;

public class PreHashedMapWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    private static void testRemoveThrowsException() {
        try {
            // Instantiating a subclass of PreHashedMap with dummy parameters
            PreHashedMap<String> map = new PreHashedMap<String>(1, 0, 0, 0) {
                @Override
                protected void init(Object[] ht) {
                    // No initialization needed for this test
                }
            };
            // Invoking remove() method, expecting UnsupportedOperationException
            map.remove();
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
