/*
 * @test
 * @summary Test for ConcurrentSkipListMap.floorEntry with null key
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testFloorEntryWithNullKey();
    }

    private static void testFloorEntryWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            Map.Entry<Integer, String> result = map.floorEntry(null);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Test passes
            System.out.println("NullPointerException correctly thrown for null key.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
