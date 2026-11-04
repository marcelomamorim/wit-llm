/*
 * @test
 * @summary Test setValue method of sun.util.PreHashedMap
 * @run main PreHashedMapWitupTest
 */

import sun.util.PreHashedMap;

public class PreHashedMapWitupTest {

    public static void main(String[] args) {
        testSetValueThrows();
    }

    private static void testSetValueThrows() {
        PreHashedMap<String> map = new PreHashedMap<String>(1, 0, 0, 0) {
            @Override
            protected void init(Object[] ht) {
                // No initialization needed for this test
            }
        };
        boolean threw = false;
        try {
            map.setValue("value");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException to be thrown by setValue") ;
        }
    }
}
