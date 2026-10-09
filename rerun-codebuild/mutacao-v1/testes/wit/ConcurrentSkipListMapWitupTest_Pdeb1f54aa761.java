/* @test    @summary Test replaceAll with null function to trigger NullPointerException    @run main ConcurrentSkipListMapWitupTest_Pdeb1f54aa761 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;

public class ConcurrentSkipListMapWitupTest_Pdeb1f54aa761 {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
    }

    private static void testReplaceAllWithNullFunction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("Expected NullPointerException when replaceAll called with null");
    }
}
