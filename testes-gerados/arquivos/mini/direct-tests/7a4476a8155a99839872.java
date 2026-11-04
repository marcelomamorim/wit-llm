/*
 * @test
 * @summary Test java.util.Hashtable.computeIfAbsent behavior
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.ConcurrentModificationException;
import java.util.function.Function;
import java.util.Objects;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testComputeIfAbsentReturnsExistingValue();
        testComputeIfAbsentAddsNewValue();
        testComputeIfAbsentReturnsNullDoesNotAdd();
        testComputeIfAbsentThrowsOnConcurrentModification();
        testComputeIfAbsentRejectsNullMappingFunction();
    }

    static void testComputeIfAbsentReturnsExistingValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        String result = ht.computeIfAbsent("key", k -> "newValue");
        if (!"value".equals(result)) {
            throw new AssertionError("Expected existing value to be returned");
        }
        if (ht.size() != 1) {
            throw new AssertionError("Hashtable size should remain 1");
        }
    }

    static void testComputeIfAbsentAddsNewValue() {
        Hashtable<String, String> ht = new Hashtable<>();
        String result = ht.computeIfAbsent("key", k -> "computedValue");
        if (!"computedValue".equals(result)) {
            throw new AssertionError("Expected computed value to be returned");
        }
        if (!"computedValue".equals(ht.get("key"))) {
            throw new AssertionError("Expected computed value to be stored in hashtable");
        }
        if (ht.size() != 1) {
            throw new AssertionError("Hashtable size should be 1 after insertion");
        }
    }

    static void testComputeIfAbsentReturnsNullDoesNotAdd() {
        Hashtable<String, String> ht = new Hashtable<>();
        String result = ht.computeIfAbsent("key", k -> null);
        if (result != null) {
            throw new AssertionError("Expected null to be returned when mappingFunction returns null");
        }
        if (ht.containsKey("key")) {
            throw new AssertionError("Hashtable should not contain key when mappingFunction returns null");
        }
        if (ht.size() != 0) {
            throw new AssertionError("Hashtable size should remain 0 when no value added");
        }
    }

    static void testComputeIfAbsentThrowsOnConcurrentModification() {
        final Hashtable<String, String> ht = new Hashtable<>();
        ht.put("key", "value");
        try {
            ht.computeIfAbsent("newKey", new Function<String, String>() {
                @Override
                public String apply(String k) {
                    // Modify hashtable during computeIfAbsent
                    ht.put("anotherKey", "anotherValue");
                    return "newValue";
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            // Expected
        }
    }

    static void testComputeIfAbsentRejectsNullMappingFunction() {
        Hashtable<String, String> ht = new Hashtable<>();
        try {
            ht.computeIfAbsent("key", null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
