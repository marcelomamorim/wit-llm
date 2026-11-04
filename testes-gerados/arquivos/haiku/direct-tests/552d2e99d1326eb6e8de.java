/*
 * @test
 * @summary Verifies that Provider.elements() returns an enumeration of the provider's current values
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ProviderWitupTest {

    public static void main(String[] args) {
        Provider p = new Provider("WitupTest", "1.0", "info") {};

        p.put("Foo", "Bar");
        Enumeration<Object> e = p.elements();
        if (e == null) {
            throw new RuntimeException("elements() returned null");
        }
        List<Object> vals = new ArrayList<>();
        while (e.hasMoreElements()) {
            vals.add(e.nextElement());
        }
        if (!vals.contains("Bar")) {
            throw new RuntimeException("Value 'Bar' not found in elements(): " + vals);
        }
        if (vals.size() != p.values().size()) {
            throw new RuntimeException("Enumeration size " + vals.size()
                    + " differs from values() size " + p.values().size());
        }

        p.put("Baz", "Qux");
        Enumeration<Object> e2 = p.elements();
        List<Object> vals2 = new ArrayList<>();
        while (e2.hasMoreElements()) {
            vals2.add(e2.nextElement());
        }
        if (!vals2.contains("Qux")) {
            throw new RuntimeException("Value 'Qux' not found after second put: " + vals2);
        }
        if (vals2.size() != p.values().size()) {
            throw new RuntimeException("Second enumeration size mismatch");
        }
    }
}
