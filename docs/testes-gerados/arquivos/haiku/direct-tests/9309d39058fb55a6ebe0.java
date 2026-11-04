/*
 * @test
 * @summary Verifies that Provider.values() returns an unmodifiable live view of the provider entries
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Collection;
import java.util.Iterator;

public class ProviderWitupTest extends Provider {

    ProviderWitupTest() {
        super("WitupTestProvider", "1.0", "witup info");
    }

    public static void main(String[] args) {
        ProviderWitupTest p = new ProviderWitupTest();

        p.put("TestKey", "TestValue");
        Collection<Object> vals = p.values();

        if (vals == null) {
            throw new RuntimeException("values() returned null");
        }
        if (!vals.contains("TestValue")) {
            throw new RuntimeException("values() does not contain the stored value");
        }

        try {
            vals.add("extra");
            throw new RuntimeException("add() should not be supported");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        try {
            vals.remove("TestValue");
            throw new RuntimeException("remove() should not be supported");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        try {
            Iterator<Object> it = vals.iterator();
            it.next();
            it.remove();
            throw new RuntimeException("Iterator.remove() should not be supported");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        if (!vals.contains("TestValue")) {
            throw new RuntimeException("Entry removed through unmodifiable view");
        }

        p.put("SecondKey", "SecondValue");
        if (!vals.contains("SecondValue")) {
            throw new RuntimeException("values() view is not live");
        }
    }
}
