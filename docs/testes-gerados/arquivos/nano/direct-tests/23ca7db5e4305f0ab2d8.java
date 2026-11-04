/* @test    @summary Test for Hashtable.replaceAll method to ensure it updates values correctly and handles nulls.    @run main HashtableWitupTest */

import java.util.Hashtable;
import java.util.function.BiFunction;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testReplaceAllUpdatesValues();
        testReplaceAllWithNullFunction();
        testReplaceAllWithNullValues();
    }

    public static void testReplaceAllUpdatesValues() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("a", 1);
        table.put("b", 2);
        table.put("c", 3);

        BiFunction<String, Integer, Integer> increment = (k, v) -> v + 10;
        table.replaceAll(increment);

        assert table.get("a") == 11 : "Expected 11, got " + table.get("a");
        assert table.get("b") == 12 : "Expected 12, got " + table.get("b");
        assert table.get("c") == 13 : "Expected 13, got " + table.get("c");
    }

    public static void testReplaceAllWithNullFunction() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("x", 5);
        try {
            table.replaceAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    public static void testReplaceAllWithNullValues() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("y", 20);
        BiFunction<String, Integer, Integer> setNull = (k, v) -> null;
        table.replaceAll(setNull);
        assert table.get("y") == null : "Expected null, got " + table.get("y");
    }
}
