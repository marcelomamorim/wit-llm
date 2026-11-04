/*
 * @test
 * @summary Test that Hashtable.add(Map.Entry) throws UnsupportedOperationException
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;
import java.util.Map;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashtableWitupTest {
    public static void main(String[] args) {
        // Create a Hashtable instance
        Hashtable<String, String> ht = new Hashtable<>();

        // Obtain the entrySet view
        AbstractSet<Map.Entry<String, String>> entrySet = (AbstractSet<Map.Entry<String, String>>) ht.entrySet();

        // Create a dummy Map.Entry implementation
        Map.Entry<String, String> dummyEntry = new Map.Entry<String, String>() {
            public String getKey() { return "key"; }
            public String getValue() { return "value"; }
            public String setValue(String value) { throw new UnsupportedOperationException(); }
        };

        // Test that add throws UnsupportedOperationException
        boolean caught = false;
        try {
            entrySet.add(dummyEntry);
        } catch (UnsupportedOperationException e) {
            caught = true;
        }

        if (!caught) {
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by entrySet.add");
        }

        // Additional sanity check: entrySet iterator behaves as expected
        Iterator<Map.Entry<String, String>> it = entrySet.iterator();
        if (it.hasNext()) {
            throw new RuntimeException("Expected empty entrySet iterator");
        }

        System.out.println("HashtableWitupTest passed");
    }
}
