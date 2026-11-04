/*
 * @test
 * @summary Test for ConcurrentSkipListMap.ceilingEntry with null key
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testCeilingEntryWithNullKey();
    }

    public static void testCeilingEntryWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.ceilingEntry(null);
            // If no exception, test fails
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
