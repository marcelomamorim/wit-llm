/*
 * @test
 * @summary Test java.util.Hashtable.forEach method for expected behavior and exceptions
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.ConcurrentModificationException;
import java.util.function.BiConsumer;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachBasicIteration();
        testForEachConcurrentModification();
        System.out.println("All tests passed.");
    }

    private static void testForEachNullAction() {
        Hashtable<String, String> ht = new Hashtable<>();
        try {
            ht.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachBasicIteration() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        ht.put("one", 1);
        ht.put("two", 2);
        ht.put("three", 3);

        final StringBuilder sb = new StringBuilder();
        BiConsumer<String, Integer> action = new BiConsumer<String, Integer>() {
            public void accept(String k, Integer v) {
                sb.append(k).append("=").append(v).append(",");
            }
        };

        ht.forEach(action);

        String result = sb.toString();
        // Check that all keys are present in the output string
        if (!(result.contains("one=1") && result.contains("two=2") && result.contains("three=3"))) {
            throw new AssertionError("forEach did not iterate all entries: " + result);
        }
    }

    private static void testForEachConcurrentModification() {
        final Hashtable<String, String> ht = new Hashtable<>();
        ht.put("a", "A");
        ht.put("b", "B");

        BiConsumer<String, String> action = new BiConsumer<String, String>() {
            private boolean modified = false;
            public void accept(String k, String v) {
                if (!modified) {
                    ht.put("c", "C"); // structural modification during iteration
                    modified = true;
                }
            }
        };

        try {
            ht.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
