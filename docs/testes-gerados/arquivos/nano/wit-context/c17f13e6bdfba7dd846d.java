/*
 * @test
 * @summary Test for the forEach method in Hashtable to verify behavior with null BiConsumer.
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.function.BiConsumer;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    static void testForEachWithNullAction() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean exceptionThrown = false;
        try {
            table.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null BiConsumer");
        }
    }

    static void testForEachWithValidAction() {
        Hashtable<Integer, String> table = new Hashtable<>();
        table.put(1, "one");
        table.put(2, "two");
        StringBuilder sb = new StringBuilder();
        BiConsumer<Integer, String> consumer = (k, v) -> sb.append(k).append(":").append(v).append(",");
        table.forEach(consumer);
        String result = sb.toString();
        if (!result.contains("1:one") || !result.contains("2:two")) {
            throw new AssertionError("forEach did not process all entries correctly: " + result);
        }
    }
}
