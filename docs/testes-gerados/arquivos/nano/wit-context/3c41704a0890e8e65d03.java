/*
 * @test
 * @summary Test replaceAll with null function to verify NullPointerException
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
    }

    public static void testReplaceAllWithNullFunction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.replaceAll(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
