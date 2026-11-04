/*
 * @test
 * @summary Test HashMap.tryAdvance(Consumer) for null argument and normal iteration
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceIteration();
        testTryAdvanceConcurrentModification();
    }

    private static void testTryAdvanceNullAction() {
        HashMap<String, String> map = new HashMap<>();
        java.util.HashMap.KeySpliterator<String, String> spliterator = map.keySpliterator();
        boolean npeThrown = false;
        try {
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testTryAdvanceIteration() {
        HashMap<String, String> map = new HashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");

        java.util.HashMap.KeySpliterator<String, String> spliterator = map.keySpliterator();

        final StringBuilder collected = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            @Override
            public void accept(String s) {
                collected.append(s).append(",");
            }
        };

        int count = 0;
        while (spliterator.tryAdvance(action)) {
            count++;
        }

        if (count != 3) {
            throw new AssertionError("Expected to iterate over 3 keys, but iterated over " + count);
        }
        String result = collected.toString();
        if (!(result.contains("one") && result.contains("two") && result.contains("three"))) {
            throw new AssertionError("Expected keys one,two,three in iteration result, got: " + result);
        }
    }

    private static void testTryAdvanceConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");

        java.util.HashMap.KeySpliterator<String, String> spliterator = map.keySpliterator();

        Consumer<String> action = new Consumer<String>() {
            @Override
            public void accept(String s) {
                // Modify map during iteration to trigger ConcurrentModificationException
                if ("a".equals(s)) {
                    map.put("c", "C");
                }
            }
        };

        boolean cmeThrown = false;
        try {
            while (spliterator.tryAdvance(action)) {
                // continue
            }
        } catch (ConcurrentModificationException e) {
            cmeThrown = true;
        }

        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException due to modification during iteration");
        }
    }
}
