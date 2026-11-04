/*
 * @test
 * @summary Test for HashMap.tryAdvance with null Consumer
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithValidConsumer();
    }

    private static void testTryAdvanceWithNullConsumer() {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            map.tryAdvance(null);
            System.out.println("FAIL: Expected NullPointerException for null Consumer");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null Consumer");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testTryAdvanceWithEmptyMap() {
        HashMap<String, Integer> map = new HashMap<>();
        Consumer<String> consumer = s -> {};
        boolean result = map.tryAdvance(consumer);
        if (result == false) {
            System.out.println("PASS: tryAdvance returns false on empty map");
        } else {
            System.out.println("FAIL: tryAdvance should return false on empty map");
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("key1", 1);
        map.put("key2", 2);
        Consumer<String> consumer = s -> {
            if (s == null) {
                throw new NullPointerException();
            }
            System.out.println("Consumed key: " + s);
        };
        boolean result = map.tryAdvance(consumer);
        if (result) {
            System.out.println("PASS: tryAdvance processed at least one element");
        } else {
            System.out.println("FAIL: tryAdvance should return true when map has elements");
        }
    }
}
