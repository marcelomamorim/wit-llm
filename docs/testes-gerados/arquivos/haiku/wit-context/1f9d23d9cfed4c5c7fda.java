/*
 * @test
 * @summary Witup-style behavioral tests for Hashtable.contains(Object)
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("one", 1);
        table.put("two", 2);

        if (!table.contains(1)) {
            throw new RuntimeException("expected contains(1) to be true");
        }
        if (!table.contains(Integer.valueOf(2))) {
            throw new RuntimeException("expected contains(2) to be true");
        }
        if (table.contains(3)) {
            throw new RuntimeException("expected contains(3) to be false");
        }
        if (table.contains("one")) {
            throw new RuntimeException("contains must check values, not keys");
        }

        table.remove("one");
        if (table.contains(1)) {
            throw new RuntimeException("expected contains(1) to be false after remove");
        }

        Hashtable<String, Integer> empty = new Hashtable<>();
        if (empty.contains(0)) {
            throw new RuntimeException("empty table must not contain 0");
        }

        try {
            table.contains(null);
            throw new RuntimeException("expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
        }

        try {
            empty.contains(null);
            throw new RuntimeException("expected NullPointerException for null argument on empty table");
        } catch (NullPointerException expected) {
        }
    }
}
