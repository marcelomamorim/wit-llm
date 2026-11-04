/*
 * @test
 * @summary Verify Hashtable enumerator nextElement throws NoSuchElementException when no elements remain
 * @run main HashtableWitupTest
 */

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testEmptyElementsThrows();
        testEmptyKeysThrows();
        testExhaustedEnumerationThrowsRepeatedly();
        testReturnedValuesAndKeys();
    }

    static void testEmptyElementsThrows() {
        Hashtable<String, Integer> h = new Hashtable<>();
        Enumeration<Integer> e = h.elements();
        expectNoSuchElement(e);
    }

    static void testEmptyKeysThrows() {
        Hashtable<String, Integer> h = new Hashtable<>();
        Enumeration<String> e = h.keys();
        expectNoSuchElement(e);
    }

    static void testExhaustedEnumerationThrowsRepeatedly() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        Enumeration<Integer> e = h.elements();
        if (!e.hasMoreElements()) {
            throw new RuntimeException("expected one element");
        }
        Integer v = e.nextElement();
        if (v.intValue() != 1) {
            throw new RuntimeException("unexpected value: " + v);
        }
        expectNoSuchElement(e);
        expectNoSuchElement(e);
    }

    static void testReturnedValuesAndKeys() {
        Hashtable<String, String> h = new Hashtable<>();
        h.put("k", "v");
        Enumeration<String> values = h.elements();
        String value = values.nextElement();
        if (!"v".equals(value)) {
            throw new RuntimeException("unexpected element: " + value);
        }
        Enumeration<String> keys = h.keys();
        String key = keys.nextElement();
        if (!"k".equals(key)) {
            throw new RuntimeException("unexpected key: " + key);
        }
        expectNoSuchElement(keys);
    }

    static void expectNoSuchElement(Enumeration<?> e) {
        try {
            e.nextElement();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new RuntimeException("expected NoSuchElementException");
    }
}
