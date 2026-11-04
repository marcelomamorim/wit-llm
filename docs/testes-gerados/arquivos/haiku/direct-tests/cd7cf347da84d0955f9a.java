/*
 * @test
 * @summary Tests argument validation of Hashtable(int, float) and basic usability after construction
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        expectIllegalArgument(-1, 0.75f);
        expectIllegalArgument(10, 0.0f);
        expectIllegalArgument(10, -0.5f);
        expectIllegalArgument(10, Float.NaN);
        expectIllegalArgument(-100, Float.NaN);

        Hashtable<String, Integer> zeroCap = new Hashtable<>(0, 0.75f);
        check(zeroCap.isEmpty(), "new table with zero capacity should be empty");
        zeroCap.put("a", 1);
        check(zeroCap.size() == 1, "size should be 1 after one put");
        check(zeroCap.get("a") == 1, "get should return stored value");

        Hashtable<String, Integer> tiny = new Hashtable<>(10, Float.MIN_VALUE);
        tiny.put("k", 5);
        check(tiny.get("k") == 5, "tiny positive load factor should be accepted and usable");

        Hashtable<String, Integer> normal = new Hashtable<>(4, 0.5f);
        for (int i = 0; i < 100; i++) {
            normal.put("key" + i, i);
        }
        check(normal.size() == 100, "size should grow past initial capacity");
        check(normal.get("key57") == 57, "entry should be retrievable after rehashing");
    }

    static void expectIllegalArgument(int initialCapacity, float loadFactor) {
        try {
            new Hashtable<String, String>(initialCapacity, loadFactor);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for capacity="
                + initialCapacity + " loadFactor=" + loadFactor);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
