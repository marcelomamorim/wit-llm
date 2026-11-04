/*
 * @test
 * @summary Test java.util.Hashtable.replaceAll with valid and invalid inputs
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllBasicFunctionality();
        testReplaceAllConcurrentModification();
        System.out.println("All tests passed.");
    }

    private static void testReplaceAllNullFunction() {
        Hashtable<String, String> ht = new Hashtable<>();
        try {
            ht.replaceAll(null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllBasicFunctionality() {
        Hashtable<String, Integer> ht = new Hashtable<>();
        ht.put("one", 1);
        ht.put("two", 2);
        ht.put("three", 3);

        BiFunction<String, Integer, Integer> increment = new BiFunction<String, Integer, Integer>() {
            public Integer apply(String k, Integer v) {
                return v + 1;
            }
        };

        ht.replaceAll(increment);

        if (!ht.get("one").equals(2)) {
            throw new AssertionError("Value for 'one' should be 2");
        }
        if (!ht.get("two").equals(3)) {
            throw new AssertionError("Value for 'two' should be 3");
        }
        if (!ht.get("three").equals(4)) {
            throw new AssertionError("Value for 'three' should be 4");
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
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
