/*
 * @test
 * @summary Test for HashMap.forEach with null action to verify NullPointerException
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.function.BiConsumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithEmptyMap();
        testForEachWithPopulatedMap();
    }

    private static void testForEachWithNullAction() {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            map.forEach(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testForEachWithEmptyMap() {
        HashMap<String, Integer> map = new HashMap<>();
        final boolean[] called = {false};
        map.forEach((k, v) -> {
            called[0] = true;
        });
        if (!called[0]) {
            System.out.println("PASS: forEach on empty map does not invoke consumer");
        } else {
            System.out.println("FAIL: Consumer invoked on empty map");
        }
    }

    private static void testForEachWithPopulatedMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        final StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> sb.append(k).append(v));
        String result = sb.toString();
        if (result.contains("a1") && result.contains("b2")) {
            System.out.println("PASS: forEach correctly iterates over entries");
        } else {
            System.out.println("FAIL: forEach did not process entries correctly");
        }
    }
}
