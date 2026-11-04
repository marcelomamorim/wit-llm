/*
 * @test
 * @summary Test HashMap.KeySpliterator.forEachRemaining with null and normal Consumer
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNullAction() {
        HashMap<Integer, String> map = new HashMap<>();
        HashMap<Integer, String>.KeySpliterator spliterator = map.keySpliterator();
        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        final StringBuilder sb = new StringBuilder();
        HashMap<Integer, String>.KeySpliterator spliterator = map.keySpliterator();
        spliterator.forEachRemaining(new Consumer<Integer>() {
            public void accept(Integer k) {
                sb.append(k);
            }
        });
        String result = sb.toString();
        if (!(result.contains("1") && result.contains("2"))) {
            throw new AssertionError("Expected keys 1 and 2 in output, got: " + result);
        }
    }

    static void testForEachRemainingConcurrentModification() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        HashMap<Integer, String>.KeySpliterator spliterator = map.keySpliterator();
        // Modify map after spliterator creation to trigger ConcurrentModificationException
        map.put(2, "two");
        try {
            spliterator.forEachRemaining(new Consumer<Integer>() {
                public void accept(Integer k) {
                    // do nothing
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException due to modification during iteration");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
