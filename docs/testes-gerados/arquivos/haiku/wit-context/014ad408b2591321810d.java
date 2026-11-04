/*
 * @test
 * @summary Witup-derived checks for the CheckedMap constructor path exposed through Collections.checkedMap
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullMapThrowsNpe();
        testNullKeyTypeThrowsNpe();
        testNullValueTypeThrowsNpe();
        testWrongKeyTypeThrowsClassCast();
        testWrongValueTypeThrowsClassCast();
        testValidWrapperAcceptsTypedEntries();
    }

    static void expectNpe(Runnable r, String name) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(name + ": expected NullPointerException");
    }

    static void expectCce(Runnable r, String name) {
        try {
            r.run();
        } catch (ClassCastException e) {
            return;
        }
        throw new RuntimeException(name + ": expected ClassCastException");
    }

    static void testNullMapThrowsNpe() {
        expectNpe(() -> Collections.checkedMap(null, String.class, Integer.class),
                  "null map");
    }

    static void testNullKeyTypeThrowsNpe() {
        expectNpe(() -> Collections.checkedMap(new HashMap<String, Integer>(), null, Integer.class),
                  "null keyType");
    }

    static void testNullValueTypeThrowsNpe() {
        expectNpe(() -> Collections.checkedMap(new HashMap<String, Integer>(), String.class, null),
                  "null valueType");
    }

    static void testWrongKeyTypeThrowsClassCast() {
        Map<String, Integer> m =
            Collections.checkedMap(new HashMap<String, Integer>(), String.class, Integer.class);
        @SuppressWarnings("unchecked")
        Map<Object, Object> raw = (Map<Object, Object>) (Map) m;
        expectCce(() -> raw.put(Integer.valueOf(1), Integer.valueOf(2)), "wrong key type");
    }

    static void testWrongValueTypeThrowsClassCast() {
        Map<String, Integer> m =
            Collections.checkedMap(new HashMap<String, Integer>(), String.class, Integer.class);
        @SuppressWarnings("unchecked")
        Map<Object, Object> raw = (Map<Object, Object>) (Map) m;
        expectCce(() -> raw.put("b", "x"), "wrong value type");
    }

    static void testValidWrapperAcceptsTypedEntries() {
        Map<String, Integer> m =
            Collections.checkedMap(new HashMap<String, Integer>(), String.class, Integer.class);
        m.put("a", Integer.valueOf(1));
        if (!Integer.valueOf(1).equals(m.get("a"))) {
            throw new RuntimeException("valid entry not retrievable");
        }
        if (m.size() != 1) {
            throw new RuntimeException("unexpected size: " + m.size());
        }
    }
}
