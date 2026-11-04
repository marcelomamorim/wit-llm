/*
 * @test
 * @summary Witup-derived checks for Hashtable.replace(K, V, V) null-argument validation and conditional replacement
 * @run main HashtableWitupTest
 */
import java.util.Hashtable;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNullOldValueThrowsAndKeepsEntry();
        testNullNewValueThrowsAndKeepsEntry();
        testBothValuesNullThrows();
        testNullOldValueValidatedBeforeLookup();
        testAbsentKeyReturnsFalse();
        testMatchingOldValueReplaces();
        testMismatchedOldValueReturnsFalse();
        testNullKeyThrowsNpe();
    }

    interface Action {
        void run();
    }

    static void expectNpe(Action action, String message) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullOldValueThrowsAndKeepsEntry() {
        final Hashtable<String, Integer> table = new Hashtable<>();
        table.put("a", 1);
        expectNpe(() -> table.replace("a", null, 2),
                  "replace with null oldValue must throw NullPointerException");
        check(table.get("a") == 1, "value must be unchanged after NPE for null oldValue");
    }

    static void testNullNewValueThrowsAndKeepsEntry() {
        final Hashtable<String, Integer> table = new Hashtable<>();
        table.put("a", 1);
        expectNpe(() -> table.replace("a", 1, null),
                  "replace with null newValue must throw NullPointerException");
        check(table.get("a") == 1, "value must be unchanged after NPE for null newValue");
    }

    static void testBothValuesNullThrows() {
        final Hashtable<String, Integer> table = new Hashtable<>();
        table.put("a", 1);
        expectNpe(() -> table.replace("a", null, null),
                  "replace with both values null must throw NullPointerException");
        check(table.get("a") == 1, "value must be unchanged after NPE for both null values");
    }

    static void testNullOldValueValidatedBeforeLookup() {
        final Hashtable<String, Integer> table = new Hashtable<>();
        expectNpe(() -> table.replace("absent", null, 2),
                  "null oldValue must be rejected even when key is absent");
        check(!table.containsKey("absent"), "absent key must not be inserted by failed replace");
    }

    static void testAbsentKeyReturnsFalse() {
        final Hashtable<String, Integer> table = new Hashtable<>();
        boolean result = table.replace("missing", 1, 2);
        check(!result, "replace on absent key must return false");
        check(!table.containsKey("missing"), "replace on absent key must not insert the key");
    }

    static void testMatchingOldValueReplaces() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("k", 10);
        boolean result = table.replace("k", 10, 20);
        check(result, "replace with matching oldValue must return true");
        check(table.get("k") == 20, "replace with matching oldValue must store newValue");
    }

    static void testMismatchedOldValueReturnsFalse() {
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("k", 10);
        boolean result = table.replace("k", 99, 20);
        check(!result, "replace with mismatched oldValue must return false");
        check(table.get("k") == 10, "replace with mismatched oldValue must not change the value");
    }

    static void testNullKeyThrowsNpe() {
        final Hashtable<String, Integer> table = new Hashtable<>();
        table.put("a", 1);
        expectNpe(() -> table.replace(null, 1, 2),
                  "replace with null key must throw NullPointerException");
        check(table.get("a") == 1, "existing entries must be unchanged after null key NPE");
    }
}
