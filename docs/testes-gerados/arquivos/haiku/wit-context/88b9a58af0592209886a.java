/*
 * @test
 * @summary Verifies that Hashtable iterators throw ConcurrentModificationException from next() after structural modification
 * @run main HashtableWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNextThrowsAfterPutNewKey();
        testNextThrowsAfterRemove();
        testNextThrowsForValuesIterator();
        testNextThrowsForEntryIterator();
        testNextWorksWithoutModification();
    }

    static void expectCME(Iterator<?> it) {
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new AssertionError("expected ConcurrentModificationException");
    }

    static void testNextThrowsAfterPutNewKey() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("a", 1);
        t.put("b", 2);
        Iterator<String> it = t.keySet().iterator();
        it.next();
        t.put("c", 3);
        expectCME(it);
    }

    static void testNextThrowsAfterRemove() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("a", 1);
        t.put("b", 2);
        Iterator<String> it = t.keySet().iterator();
        it.next();
        t.remove("b");
        expectCME(it);
    }

    static void testNextThrowsForValuesIterator() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("a", 1);
        t.put("b", 2);
        Iterator<Integer> it = t.values().iterator();
        it.next();
        t.put("z", 9);
        expectCME(it);
    }

    static void testNextThrowsForEntryIterator() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("a", 1);
        t.put("b", 2);
        Iterator<?> it = t.entrySet().iterator();
        it.next();
        t.put("y", 7);
        expectCME(it);
    }

    static void testNextWorksWithoutModification() {
        Hashtable<String, Integer> t = new Hashtable<>();
        t.put("a", 1);
        t.put("b", 2);
        t.put("c", 3);
        Iterator<String> it = t.keySet().iterator();
        Set<String> seen = new HashSet<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }
        if (!seen.equals(new HashSet<>(t.keySet()))) {
            throw new AssertionError("iteration did not return all keys: " + seen);
        }
    }
}
