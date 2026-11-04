/*
 * @test
 * @summary Verifies that entries created via Map.entry (backed by KeyValueHolder) reject setValue and keep their value unchanged
 * @run main KeyValueHolderWitupTest
 */

import java.util.Map;

public class KeyValueHolderWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetValueThrowsUnsupported() {
        Map.Entry<String, String> e = Map.entry("k", "v");
        boolean thrown = false;
        try {
            e.setValue("other");
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        check(thrown, "setValue must throw UnsupportedOperationException");
    }

    static void testSetValueWithNullArgumentStillThrows() {
        Map.Entry<String, String> e = Map.entry("k", "v");
        boolean thrown = false;
        try {
            e.setValue(null);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        check(thrown, "setValue(null) must throw UnsupportedOperationException");
    }

    static void testValueUnchangedAfterSetValueAttempt() {
        Map.Entry<String, String> e = Map.entry("k", "v");
        try {
            e.setValue("other");
        } catch (UnsupportedOperationException ignored) {
        }
        check("k".equals(e.getKey()), "key changed after setValue attempt");
        check("v".equals(e.getValue()), "value changed after setValue attempt");
    }

    public static void main(String[] args) {
        testSetValueThrowsUnsupported();
        testSetValueWithNullArgumentStillThrows();
        testValueUnchangedAfterSetValueAttempt();
    }
}
