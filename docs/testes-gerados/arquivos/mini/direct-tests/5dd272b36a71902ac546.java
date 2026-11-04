/*
 * @test
 * @summary Test that PreHashedMap.remove() throws UnsupportedOperationException
 * @run main PreHashedMapWitupTest
 */

public class PreHashedMapWitupTest {
    public static void main(String[] args) {
        final sun.util.PreHashedMap<?> map = new sun.util.PreHashedMap<Object>(1, 0, 0, 0) {
            protected void init(Object[] ht) {
                // no entries
            }
        };

        try {
            map.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
