/*
 * @test
 * @summary Test java.util.Hashtable.forEach method behavior
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.ConcurrentModificationException;
import java.util.function.BiConsumer;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testForEachBasic();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachBasic() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        ht.put("one", 1);
        ht.put("two", 2);
        ht.put("three", 3);

        final int[] sum = {0};
        BiConsumer<String, Integer> action = new BiConsumer<String, Integer>() {
            public void accept(String k, Integer v) {
                if (k == null || v == null) {
                    throw new AssertionError("Key or value is null");
                }
                sum[0] += v;
            }
        };

        ht.forEach(action);

        if (sum[0] != 6) {
            throw new AssertionError("Sum of values expected 6 but was " + sum[0]);
        }
    }

    static void testForEachNullAction() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");

        try {
            ht.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testForEachConcurrentModification() {
        final Hashtable<String, String> ht = new Hashtable<>();
        ht.put("a", "A");
        ht.put("b", "B");

        BiConsumer<String, String> action = new BiConsumer<String, String>() {
            boolean first = true;
            public void accept(String k, String v) {
                if (first) {
                    first = false;
                    ht.put("c", "C"); // structural modification during iteration
                }
            }
        };

        try {
            ht.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during forEach");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
