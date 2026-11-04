/*
 * @test
 * @summary Witup-derived tests for HashMap.replaceAll null function and entry replacement behavior
 * @run main HashMapWitupTest
 */
import java.util.HashMap;
import java.util.function.BiFunction;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testAppliesFunctionToAllEntries();
        testEmptyMapDoesNotInvokeFunction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFunctionThrowsNPE() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        boolean thrown = false;
        try {
            m.replaceAll(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "replaceAll(null) must throw NullPointerException");
        check(m.get("a") == 1, "map must be unchanged after NullPointerException");
    }

    static void testAppliesFunctionToAllEntries() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        BiFunction<String, Integer, Integer> f =
            (k, v) -> k.equals("a") ? v + 10 : v * 3;
        m.replaceAll(f);
        check(m.get("a") == 11, "value for a should be 11");
        check(m.get("b") == 6, "value for b should be 6");
        check(m.size() == 2, "size should remain 2");
    }

    static void testEmptyMapDoesNotInvokeFunction() {
        HashMap<String, Integer> m = new HashMap<>();
        int[] calls = {0};
        m.replaceAll((k, v) -> {
            calls[0]++;
            return v;
        });
        check(calls[0] == 0, "function must not be invoked on an empty map");
        check(m.size() == 0, "empty map size must remain 0");
    }
}
