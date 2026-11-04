/* @test    @summary Test computeIfAbsent method of Hashtable for null and non-null values    @run main HashtableWitupTest */

import java.util.Hashtable;
import java.util.function.Function;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testComputeIfAbsentWithNullMappingFunction();
        testComputeIfAbsentWithNonNullMappingFunction();
        testComputeIfAbsentWithExistingKey();
    }

    static void testComputeIfAbsentWithNullMappingFunction() {
        Hashtable<String, String> table = new Hashtable<>();
        try {
            table.computeIfAbsent("key", null);
        } catch (NullPointerException e) {
            System.out.println("Passed: computeIfAbsent throws NullPointerException for null mappingFunction");
            return;
        }
        throw new AssertionError("Failed: computeIfAbsent did not throw NullPointerException for null mappingFunction");
    }

    static void testComputeIfAbsentWithNonNullMappingFunction() {
        Hashtable<String, String> table = new Hashtable<>();
        Function<String, String> func = k -> "value";
        String result = table.computeIfAbsent("key", func);
        if ("value".equals(result) && "value".equals(table.get("key"))) {
            System.out.println("Passed: computeIfAbsent inserts new value when key absent");
        } else {
            throw new AssertionError("Failed: computeIfAbsent did not insert new value correctly");
        }
    }

    static void testComputeIfAbsentWithExistingKey() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key", "existing");
        Function<String, String> func = k -> "newValue";
        String result = table.computeIfAbsent("key", func);
        if ("existing".equals(result) && "existing".equals(table.get("key"))) {
            System.out.println("Passed: computeIfAbsent does not overwrite existing key");
        } else {
            throw new AssertionError("Failed: computeIfAbsent overwrote existing key");
        }
    }
}
