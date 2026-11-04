/*
 * @test
 * @summary Test java.security.Provider.keySet() method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Set;
import java.util.Iterator;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass of Provider to test keySet()
        Provider p = new Provider("TestProvider", 1.0, "Test Provider Info") {
            private static final long serialVersionUID = 1L;
        };

        // Initially, the keySet should be empty
        Set<Object> keys = p.keySet();
        if (keys == null) {
            throw new RuntimeException("keySet() returned null");
        }
        if (!keys.isEmpty()) {
            throw new RuntimeException("keySet() should be empty initially");
        }

        // Add a property and verify keySet reflects it
        p.put("key1", "value1");
        Set<Object> keysAfterPut = p.keySet();
        if (!keysAfterPut.contains("key1")) {
            throw new RuntimeException("keySet() does not contain added key");
        }

        // Verify that the returned set is unmodifiable
        try {
            keysAfterPut.add("key2");
            throw new RuntimeException("keySet() returned set should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Verify that the keySet reflects current keys
        p.put("key2", "value2");
        Set<Object> keysAfterSecondPut = p.keySet();
        if (!keysAfterSecondPut.contains("key2")) {
            throw new RuntimeException("keySet() does not contain second added key");
        }

        // Verify that keySet contains all keys
        if (!(keysAfterSecondPut.contains("key1") && keysAfterSecondPut.contains("key2"))) {
            throw new RuntimeException("keySet() does not contain all keys");
        }

        // Verify iteration over keySet
        Iterator<Object> it = keysAfterSecondPut.iterator();
        boolean foundKey1 = false;
        boolean foundKey2 = false;
        while (it.hasNext()) {
            Object k = it.next();
            if ("key1".equals(k)) foundKey1 = true;
            if ("key2".equals(k)) foundKey2 = true;
        }
        if (!foundKey1 || !foundKey2) {
            throw new RuntimeException("keySet() iterator missing keys");
        }
    }
}
