/*
 * @test
 * @summary Test for Hashtable.replaceAll with null function
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.function.BiFunction;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
        testReplaceAllWithValidFunction();
    }

    private static void testReplaceAllWithNullFunction() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        boolean exceptionThrown = false;
        try {
            table.replaceAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null function");
        }
    }

    private static void testReplaceAllWithValidFunction() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("a", 1);
        table.put("b", 2);
        BiFunction<String, Integer, Integer> incrementFunction = (k, v) -> v + 10;
        table.replaceAll(incrementFunction);
        if (table.get("a") != 11 || table.get("b") != 12) {
            throw new AssertionError("replaceAll did not apply function correctly");
        }
    }
}
