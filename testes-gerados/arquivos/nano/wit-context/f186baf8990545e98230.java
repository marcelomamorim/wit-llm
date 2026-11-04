/* @test    @summary Test for TreeMap.forEachRemaining with null Consumer    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.function.Consumer;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
    }

    public static void testForEachRemainingNullConsumer() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        TreeMap<Integer, String>.ValueSpliterator spliterator = map.new ValueSpliterator();
        try {
            spliterator.forEachRemaining(null);
            // Expect NullPointerException, test passes if exception is thrown
        } catch (NullPointerException e) {
            // Test passes
        } catch (Exception e) {
            throw new AssertionError("Expected NullPointerException, but got: " + e);
        }
    }
}
