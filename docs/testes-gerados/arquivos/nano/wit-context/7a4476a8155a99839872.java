/*
 * @test
 * @summary Tests for java.util.Hashtable.computeIfAbsent method
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.function.Function;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testComputeIfAbsentWithNullMappingFunction();
        testComputeIfAbsentWithExistingKey();
        testComputeIfAbsentWithNullValue();
        testComputeIfAbsentWithNewKey();
    }

    private static void testComputeIfAbsentWithNullMappingFunction() {
        Hashtable<String, String> table = new Hashtable<>();
        String key = "test";
        try {
            table.computeIfAbsent(key, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testComputeIfAbsentWithExistingKey() {
        Hashtable<String, String> table = new Hashtable<>();
        String key = "existing";
        String value = "value";
        table.put(key, value);
        Function<String, String> func = k -> { throw new AssertionError("Function should not be called"); };
        String result = table.computeIfAbsent(key, func);
        if (!result.equals(value)) {
            throw new AssertionError("Result does not match existing value");
        }
    }

    private static void testComputeIfAbsentWithNullValue() {
        Hashtable<String, String> table = new Hashtable<>();
        String key = "nullValue";
        Function<String, String> func = k -> null;
        String result = table.computeIfAbsent(key, func);
        if (result != null) {
            throw new AssertionError("Expected null result");
        }
        if (table.containsKey(key)) {
            throw new AssertionError("Table should not contain key after null value");
        }
    }

    private static void testComputeIfAbsentWithNewKey() {
        Hashtable<String, String> table = new Hashtable<>();
        String key = "newKey";
        String expectedValue = "computed";
        Function<String, String> func = k -> expectedValue;
        String result = table.computeIfAbsent(key, func);
        if (!result.equals(expectedValue)) {
            throw new AssertionError("Result does not match computed value");
        }
        if (!table.containsKey(key)) {
            throw new AssertionError("Table should contain the new key");
        }
        if (!table.get(key).equals(expectedValue)) {
            throw new AssertionError("Stored value does not match computed value");
        }
    }
}
