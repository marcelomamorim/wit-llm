/* @test    @summary Test for Hashtable.next() method to ensure correct iteration behavior and concurrent modification detection.    @run main HashtableWitupTest */

import java.util.Hashtable;
import java.util.ConcurrentModificationException;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextConcurrentModification();
    }

    static void testNextNormal() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("a", "alpha");
        table.put("b", "beta");
        table.put("c", "gamma");
        java.util.Enumeration<String> enumeration = table.keys();
        boolean foundA = false, foundB = false, foundC = false;
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            if (key.equals("a")) {
                foundA = true;
            } else if (key.equals("b")) {
                foundB = true;
            } else if (key.equals("c")) {
                foundC = true;
            }
        }
        assert foundA && foundB && foundC : "Enumeration did not contain all keys";
    }

    static void testNextConcurrentModification() {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("x", "ex");
        table.put("y", "why");
        java.util.Enumeration<String> enumeration = table.keys();
        table.put("z", "zee"); // modify after enumeration creation
        try {
            enumeration.nextElement();
            assert false : "Expected ConcurrentModificationException was not thrown";
        } catch (ConcurrentModificationException e) {
            // expected exception
        }
    }
}
