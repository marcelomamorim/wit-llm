/*
 * @test
 * @summary Verify that entries exposed by Collections.unmodifiableMap reject setValue with UnsupportedOperationException and leave the backing map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testSetValueThrowsUnsupportedOperation();
        testSetValueWithNullThrowsUnsupportedOperation();
        testEntryValueUnchangedAfterFailedSetValue();
    }

    static void testSetValueThrowsUnsupportedOperation() {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("a", 1);
        Map<String, Integer> unmod = Collections.unmodifiableMap(backing);
        Iterator<Map.Entry<String, Integer>> it = unmod.entrySet().iterator();
        Map.Entry<String, Integer> e = it.next();
        boolean thrown = false;
        try {
            e.setValue(2);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setValue did not throw UnsupportedOperationException");
        }
    }

    static void testSetValueWithNullThrowsUnsupportedOperation() {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("k", 7);
        Map<String, Integer> unmod = Collections.unmodifiableMap(backing);
        Map.Entry<String, Integer> e = unmod.entrySet().iterator().next();
        boolean thrown = false;
        try {
            e.setValue(null);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setValue(null) did not throw UnsupportedOperationException");
        }
    }

    static void testEntryValueUnchangedAfterFailedSetValue() {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("a", 1);
        Map<String, Integer> unmod = Collections.unmodifiableMap(backing);
        Map.Entry<String, Integer> e = unmod.entrySet().iterator().next();
        try {
            e.setValue(99);
        } catch (UnsupportedOperationException ignored) {
            // expected
        }
        if (e.getValue().intValue() != 1) {
            throw new RuntimeException("entry value changed: " + e.getValue());
        }
        if (backing.get("a").intValue() != 1) {
            throw new RuntimeException("backing map changed: " + backing.get("a"));
        }
    }
}
