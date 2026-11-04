/*
 * @test
 * @summary Verifies Provider.keys() enumerates the keys of entries stored in an initialized provider
 * @run main ProviderWitupTest
 */

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("WitupTestProvider", "1.0", "test info");
        }
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();
        p.put("MyKey", "v1");
        p.put("Other", "v2");

        Set<Object> found = new HashSet<>();
        Enumeration<Object> e = p.keys();
        while (e.hasMoreElements()) {
            found.add(e.nextElement());
        }

        if (!found.contains("MyKey")) {
            throw new RuntimeException("keys() did not contain MyKey: " + found);
        }
        if (!found.contains("Other")) {
            throw new RuntimeException("keys() did not contain Other: " + found);
        }
    }
}
