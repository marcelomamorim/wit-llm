/*
 * @test
 * @summary Test java.util.Hashtable.computeIfAbsent behavior including null mappingFunction
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.function.Function;
import java.util.ConcurrentModificationException;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testComputeIfAbsentReturnsExistingValue();
        testComputeIfAbsentAddsNewValue();
        testComputeIfAbsentNullMappingFunction();
        testComputeIfAbsentConcurrentModification();
    }

    private static void testComputeIfAbsentReturnsExistingValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        String result = ht.computeIfAbsent("key", new Function<String, String>() {
            public String apply(String k) {
                return "newValue";
            }
        });
        if (!"value".equals(result)) {
            throw new AssertionError("Expected existing value to be returned");
        }
    }

    private static void testComputeIfAbsentAddsNewValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        String result = ht.computeIfAbsent("key", new Function<String, String>() {
            public String apply(String k) {
                return "computedValue";
            }
        });
        if (!"computedValue".equals(result)) {
            throw new AssertionError("Expected computed value to be returned");
        }
        if (!"computedValue".equals(ht.get("key"))) {
            throw new AssertionError("Expected computed value to be stored in hashtable");
        }
    }

    private static void testComputeIfAbsentNullMappingFunction() {
        Hashtable<String, String> ht = new Hashtable<>();
        try {
            ht.computeIfAbsent("key", null);
            throw new AssertionError("Expected NullPointerException for null mappingFunction");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testComputeIfAbsentConcurrentModification() {
        final Hashtable<String, String> ht = new Hashtable<>();
        // We simulate concurrent modification by subclassing and modifying modCount
        // Since modCount is private, we cannot modify it directly, so this test
        // is limited to checking that if modCount changes during apply, ConcurrentModificationException is thrown.
        // We do this by overriding apply to modify the hashtable during computation.

        Function<String, String> mappingFunction = new Function<String, String>() {
            public String apply(String k) {
                ht.put("otherKey", "otherValue"); // modifies modCount
                return "newValue";
            }
        };

        try {
            ht.computeIfAbsent("key", mappingFunction);
            throw new AssertionError("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
