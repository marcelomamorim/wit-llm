/* @test    @summary Test for TreeMap.forEachRemaining with null Consumer    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.Map;
import java.util.function.Consumer;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
    }

    public static void testForEachRemainingNullAction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            map.forEachRemaining(null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
