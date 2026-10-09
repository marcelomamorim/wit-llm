/* @test    @summary Test tailMap with null fromKey to verify NullPointerException is thrown    @run main ConcurrentSkipListMapWitupTest_P6e5514c3ede8 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P6e5514c3ede8 {

    public static void main(String[] args) {
        testTailMapNullFromKey();
    }

    private static void testTailMapNullFromKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.tailMap(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
