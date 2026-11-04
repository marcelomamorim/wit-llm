/*
 * @test
 * @summary Test tryAdvance method of ConcurrentHashMap for null action
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithValidAction();
    }

    static void testTryAdvanceWithNullAction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        try {
            map.tryAdvance(null);
            System.out.println("FAIL: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testTryAdvanceWithEmptyMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Consumer<String> action = s -> System.out.println("Visited: " + s);
        boolean result = map.tryAdvance(action);
        if (result == false) {
            System.out.println("PASS: tryAdvance returns false on empty map");
        } else {
            System.out.println("FAIL: tryAdvance should return false on empty map");
        }
    }

    static void testTryAdvanceWithValidAction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Consumer<String> action = s -> System.out.println("Visited: " + s);
        boolean result = map.tryAdvance(action);
        if (result == true) {
            System.out.println("PASS: tryAdvance returns true when element exists");
        } else {
            System.out.println("FAIL: tryAdvance should return true when element exists");
        }
    }
}
