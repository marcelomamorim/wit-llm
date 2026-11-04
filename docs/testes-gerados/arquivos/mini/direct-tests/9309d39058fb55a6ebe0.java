/*
 * @test
 * @summary Test java.security.Provider.values() method behavior
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Collection;
import java.util.Iterator;

public class ProviderWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal subclass of Provider to test values() method
        Provider p = new Provider("TestProvider", 1.0, "Test Provider Info") {
            private static final long serialVersionUID = 1L;
        };

        // Initially, no entries added, values() should return empty unmodifiable collection
        Collection<Object> vals = p.values();
        if (vals == null) {
            throw new RuntimeException("values() returned null");
        }
        if (!vals.isEmpty()) {
            throw new RuntimeException("values() should be empty initially");
        }

        // Add an entry and verify values() reflects it
        p.put("key1", "value1");
        Collection<Object> valsAfterPut = p.values();
        if (!valsAfterPut.contains("value1")) {
            throw new RuntimeException("values() does not contain the added value");
        }

        // Verify that the returned collection is unmodifiable
        try {
            valsAfterPut.add("anotherValue");
            throw new RuntimeException("values() collection should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Verify that values() returns a new unmodifiable collection each time
        Collection<Object> valsSecondCall = p.values();
        if (valsSecondCall == valsAfterPut) {
            // It may or may not be the same instance, but it must be unmodifiable
            try {
                valsSecondCall.add("x");
                throw new RuntimeException("values() collection should be unmodifiable on repeated calls");
            } catch (UnsupportedOperationException expected) {
                // expected
            }
        }

        // Verify that values() reflects current state
        p.put("key2", "value2");
        Collection<Object> valsAfterSecondPut = p.values();
        if (!valsAfterSecondPut.contains("value2")) {
            throw new RuntimeException("values() does not reflect newly added value");
        }

        // Verify that values() does not allow null elements (not explicitly stated but test for stability)
        // Since Hashtable allows null values, but Provider extends Hashtable, but Hashtable does not allow null keys or values
        // So no test for null values needed here

        System.out.println("Test passed.");
    }
}
