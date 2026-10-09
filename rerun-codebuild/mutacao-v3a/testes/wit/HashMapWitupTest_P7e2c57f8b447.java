/* @test    @summary Test the HashMap forEach method behavior, especially handling null actions.    @run main HashMapWitupTest_P7e2c57f8b447 */

import java.util.HashMap;
import java.util.function.BiConsumer;

public class HashMapWitupTest_P7e2c57f8b447 {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithEmptyMap();
        testForEachWithEntries();
    }

    static void testForEachWithNullAction() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when action is null";
    }

    static void testForEachWithEmptyMap() {
        HashMap<Integer, String> map = new HashMap<>();
        final boolean[] called = {false};
        map.forEach((k, v) -> {
            called[0] = true;
        });
        assert !called[0] : "forEach should not call action on empty map";
    }

    static void testForEachWithEntries() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        final StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> {
            sb.append(k).append(":").append(v).append(",");
        });
        String result = sb.toString();
        boolean containsOne = result.contains("1:one");
        boolean containsTwo = result.contains("2:two");
        assert containsOne && containsTwo : "forEach did not process all entries";
    }
}
