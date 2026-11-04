/*
 * @test
 * @summary Test java.util.Hashtable.replaceAll method behavior
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testReplaceAllBasic();
        testReplaceAllNullFunction();
        testReplaceAllConcurrentModification();
    }

    private static void testReplaceAllBasic() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        ht.put("one", 1);
        ht.put("two", 2);
        ht.put("three", 3);

        BiFunction<String, Integer, Integer> addOne = new BiFunction<String, Integer, Integer>() {
            public Integer apply(String k, Integer v) {
                return v + 1;
            }
        };

        ht.replaceAll(addOne);

        if (!ht.get("one").equals(2)) {
            throw new AssertionError("Expected value 2 for key 'one', got " + ht.get("one"));
        }
        if (!ht.get("two").equals(3)) {
            throw new AssertionError("Expected value 3 for key 'two', got " + ht.get("two"));
        }
        if (!ht.get("three").equals(4)) {
            throw new AssertionError("Expected value 4 for key 'three', got " + ht.get("three"));
        }
    }

    private static void testReplaceAllNullFunction() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        ht.put("key", 1);

        try {
            ht.replaceAll(null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testReplaceAllConcurrentModification() {
        final Hashtable<String, Integer> ht = new Hashtable<>();
        ht.put("a", 1);
        ht.put("b", 2);

        BiFunction<String, Integer, Integer> func = new BiFunction<String, Integer, Integer>() {
            public Integer apply(String k, Integer v) {
                if ("a".equals(k)) {
                    ht.put("c", 3); // modifies the hashtable during replaceAll
                }
                return v;
            }
        };

        try {
            ht.replaceAll(func);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during replaceAll");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
