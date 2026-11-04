/*
 * @test
 * @summary Verifies DescendingKeyIterator.remove() exception and removal behavior through the public descendingKeySet() iterator
 * @run main TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsISE();
        testRemoveAfterNextRemovesEntry();
        testRemoveTwiceThrowsISE();
        testRemoveAfterStructuralModificationThrowsCME();
    }

    static TreeMap<Integer, String> newMap() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        return m;
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testRemoveBeforeNextThrowsISE() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.descendingKeySet().iterator();
        try {
            it.remove();
            fail("remove() sem next() deveria lancar IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testRemoveAfterNextRemovesEntry() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.descendingKeySet().iterator();
        Integer k = it.next();
        if (k.intValue() != 3) {
            fail("primeira chave descendente esperada: 3, obtida: " + k);
        }
        it.remove();
        if (m.containsKey(3)) {
            fail("chave 3 deveria ter sido removida do mapa");
        }
        if (m.size() != 2) {
            fail("tamanho esperado 2, obtido: " + m.size());
        }
        if (m.firstKey().intValue() != 1 || m.lastKey().intValue() != 2) {
            fail("chaves restantes inesperadas: " + m.keySet());
        }
    }

    static void testRemoveTwiceThrowsISE() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.descendingKeySet().iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            fail("segundo remove() sem novo next() deveria lancar IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testRemoveAfterStructuralModificationThrowsCME() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.descendingKeySet().iterator();
        it.next();
        m.put(4, "d");
        try {
            it.remove();
            fail("remove() apos modificacao estrutural deveria lancar ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
