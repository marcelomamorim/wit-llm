/*
 * @test
 * @summary Test sun.util.PreHashedMap.remove() throws UnsupportedOperationException
 * @run main PreHashedMapWitupTest
 */

public class PreHashedMapWitupTest {

    public static void main(String[] args) {
        // Anonymous subclass to instantiate abstract PreHashedMap
        sun.util.PreHashedMap<Object> map = new sun.util.PreHashedMap<Object>(1, 0, 0, 0) {
            @Override
            protected void init(Object[] ht) {
                // no initialization needed for this test
            }
        };

        try {
            map.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception caught, test passes
        }
    }
}
