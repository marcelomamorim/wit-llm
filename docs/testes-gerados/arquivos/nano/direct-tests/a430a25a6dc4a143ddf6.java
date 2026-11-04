/*
 * @test
 * @summary Test tryAdvance method of ConcurrentHashMap for correct behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNonNullAction();
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyMap();
    }

    static void testTryAdvanceWithNonNullAction() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder result = new StringBuilder();
        Consumer<String> action = s -> result.append(s).append(",");
        boolean hasNext = map.tryAdvance(action);
        assert hasNext : "Expected true from tryAdvance with non-empty map";
        String output = result.toString();
        assert output.contains("one") || output.contains("two") || output.contains("three") : "Output should contain map values";
    }

    static void testTryAdvanceWithNullAction() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        boolean exceptionThrown = false;
        try {
            map.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when action is null";
    }

    static void testTryAdvanceWithEmptyMap() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        boolean resultFlag = false;
        boolean exceptionThrown = false;
        try {
            resultFlag = map.tryAdvance(s -> {});
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assert !resultFlag : "tryAdvance should return false for empty map";
        assert !exceptionThrown : "No exception should be thrown for empty map";
    }
}
