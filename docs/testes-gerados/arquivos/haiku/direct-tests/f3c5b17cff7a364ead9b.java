/*
 * @test
 * @summary Basic observable behavior of Provider.keys() on an initialized provider
 * @run main ProviderWitupTest
 */

import java.security.Provider;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ProviderWitupTest {

    static class TestProvider extends Provider {
        TestProvider() {
            super("TestProv", "1.0", "info");
        }
    }

    static Set<Object> collect(Enumeration<Object> e) {
        Set<Object> result = new HashSet<>();
        while (e.hasMoreElements()) {
            result.add(e.nextElement());
        }
        return result;
    }

    public static void main(String[] args) {
        TestProvider p = new TestProvider();

        Enumeration<Object> e0 = p.keys();
        if (e0 == null) {
            throw new RuntimeException("keys() returned null");
        }
        Set<Object> before = collect(e0);

        p.put("WitupKey", "WitupValue");
        Set<Object> afterPut = collect(p.keys());
        if (!afterPut.contains("WitupKey")) {
            throw new RuntimeException("keys() does not contain added key");
        }
        if (afterPut.size() != before.size() + 1) {
            throw new RuntimeException("unexpected key count after put: " + afterPut.size());
        }

        p.remove("WitupKey");
        Set<Object> afterRemove = collect(p.keys());
        if (afterRemove.contains("WitupKey")) {
            throw new RuntimeException("keys() still contains removed key");
        }
        if (!afterRemove.equals(before)) {
            throw new RuntimeException("keys() differs from original state after remove");
        }
    }
}
